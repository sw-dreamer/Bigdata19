import pandas as pd
import yfinance as yf
import FinanceDataReader as fdr
from tqdm import tqdm
import time
import random
import json
import concurrent.futures
import logging  # 로그 모듈을 추가

# 로그 설정
logging.basicConfig(
    filename='stock_data_processing.log',  # 로그 파일 이름
    level=logging.INFO,  # 로그 레벨 (INFO 이상부터 기록)
    format='%(asctime)s - %(levelname)s - %(message)s',  # 로그 메시지 포맷
)

# 각 주식 시장의 종목 목록 가져오기 (FinanceDataReader 사용)
def get_stock_list(market):
    if market == 'NASDAQ':
        return fdr.StockListing('NASDAQ')
    elif market == 'NYSE':
        return fdr.StockListing('NYSE')
    elif market == 'AMEX':
        return fdr.StockListing('AMEX')
    elif market == 'S&P 500':
        return fdr.StockListing('S&P500')
    else:
        return None  # 지원하지 않는 시장은 None 반환

# 각 주식 시장 데이터 가져오기
markets = ['NASDAQ', 'NYSE', 'AMEX', 'S&P 500']

all_stocks = []

for market in markets:
    market_data = get_stock_list(market)
    
    if market_data is not None and not market_data.empty:
        market_data['Market'] = market  # 각 시장에 대한 정보 추가
        all_stocks.append(market_data)
        logging.info(f"Successfully fetched {market} data.")
    else:
        logging.warning(f"Warning: No data found for {market} market")

# 모든 시장의 종목 데이터를 하나로 합침
if all_stocks:
    all_stocks_df = pd.concat(all_stocks, ignore_index=True)
    all_stocks_df.to_csv('usstock.csv', index=False, encoding='utf-8-sig')

    stockdata = pd.read_csv('usstock.csv', encoding='utf-8-sig')

    # 주어진 종목들의 데이터를 병렬로 가져오기
    def get_stock_data(ticker):
        retries = 3  # 최대 3번까지 재시도
        delay = 60  # 초기 대기시간 60초로 설정 (기존보다 더 긴 대기 시간 설정)
        for attempt in range(retries):
            try:
                stock = yf.Ticker(ticker)
                info = stock.info
                if not info or 'longName' not in info or 'sector' not in info:
                    logging.warning(f"Missing data for {ticker}")
                    return None
                return {
                    'Ticker': ticker,
                    'Name': info.get('longName', 'N/A'),
                    'Sector': info.get('sector', 'N/A'),
                    'Industry': info.get('industry', 'N/A'),
                    'Market Cap': info.get('marketCap', 'N/A')
                }
            except Exception as e:
                logging.error(f"Error fetching data for {ticker} on attempt {attempt+1}: {e}")
                if '404 Client Error' in str(e) or 'NoneType' in str(e):
                    logging.warning(f"Error for {ticker}: Will retry later")
                    return None  # 재시도는 하나의 'None' 반환
                if attempt < retries - 1:
                    time.sleep(delay)  # 대기 시간 후 재시도
                    delay *= 2  # 재시도마다 대기 시간을 두 배로 증가시킴
                else:
                    logging.error(f"Failed to fetch data for {ticker} after {retries} attempts.")
                    return None  # 재시도 끝에 실패하면 None 반환

    tickers = stockdata['Symbol'].tolist()

    # 배치로 처리 (예: 한 번에 1개씩 처리)
    batch_size = 1  # 한 번에 하나씩만 처리
    results = []
    failed_tickers = []  # 실패한 종목들을 추적하는 리스트
    final_failed_tickers = []  # 마지막에 처리되지 않은 종목을 추적
    
    start_time = time.time()  # 전체 코드 실행 시간 시작

    for i in tqdm(range(0, len(tickers), batch_size), desc="Fetching stock data in batches"):
        batch_start_time = time.time()  # 배치 처리 시간 시작
        batch_tickers = tickers[i:i+batch_size]
        
        with concurrent.futures.ThreadPoolExecutor(max_workers=3) as executor:  # 최대 3개 병렬 요청
            batch_results = list(executor.map(get_stock_data, batch_tickers))
            results.extend(batch_results)

            # 배치마다 진행 중인 종목의 정보를 출력
            for ticker, result in zip(batch_tickers, batch_results):
                if result is not None:
                    logging.info(f"Processed: {ticker} | Sector: {result.get('Sector', 'N/A')} | Market Cap: {result.get('Market Cap', 'N/A')}")
                else:
                    failed_tickers.append(ticker)  # 실패한 종목들을 추적
            
        batch_end_time = time.time()  # 배치 처리 시간 종료
        batch_duration = batch_end_time - batch_start_time
        logging.info(f"Batch {i//batch_size + 1} processed in {batch_duration:.2f} seconds.")
        
        # 각 배치가 끝난 후 대기 시간을 설정하려면 이 코드를 활성화:
        time.sleep(random.uniform(2, 4))  # 각 배치가 끝난 후 2~4초 대기

    end_time = time.time()  # 전체 코드 실행 시간 종료
    total_duration = end_time - start_time
    logging.info(f"Total execution time: {total_duration:.2f} seconds.")

    # 유효한 데이터만 필터링
    sector_data = [result for result in results if result is not None]

    # 결과를 JSON 파일로 저장
    with open('sector_data.json', 'w', encoding='utf-8') as json_file:
        json.dump(sector_data, json_file, ensure_ascii=False, indent=4)

    # 결과를 CSV 파일로 저장
    if sector_data:
        df = pd.DataFrame(sector_data)
        df.to_csv('sector_data.csv', index=False, encoding='utf-8-sig')
        logging.info("Data has been written to sector_data.csv")

    # 실패한 종목 출력
    if failed_tickers:
        logging.warning("\nFailed tickers (to retry later):")
        for ticker in failed_tickers:
            logging.warning(f"- {ticker}")
        
        # 실패한 종목의 데이터를 가져와서 저장
        failed_stock_data = []

        for ticker in failed_tickers:
            failed_stock_data.append({
                'Ticker': ticker,
                'Name': 'N/A',  # 데이터를 얻지 못했으므로 'N/A'
                'Sector': 'N/A',
                'Industry': 'N/A',
                'Market Cap': 'N/A'
            })

        # 실패한 종목 데이터를 CSV로 저장
        failed_df = pd.DataFrame(failed_stock_data)
        failed_df.to_csv('failed_tickers.csv', index=False, encoding='utf-8-sig')
        logging.info("Failed tickers have been written to failed_tickers.csv")

        # 실패한 종목들을 파일로 저장하여 나중에 다시 시도할 수 있도록 합니다.
        with open('failed_tickers.json', 'w', encoding='utf-8') as json_file:
            json.dump(failed_tickers, json_file, ensure_ascii=False, indent=4)
        
    else:
        final_failed_tickers = []

    # 나중에 처리할 실패한 종목을 최종 결과로 저장
    if final_failed_tickers:
        with open('final_failed_tickers.json', 'w', encoding='utf-8') as json_file:
            json.dump(final_failed_tickers, json_file, ensure_ascii=False, indent=4)

    # 나중에 처리할 실패한 종목을 다시 시도하기 위한 코드 추가

    # final_failed_tickers.json을 로드하여 실패한 종목 목록 가져오기
    if final_failed_tickers:
        logging.info("\nRetrying failed tickers from final_failed_tickers.json...")

        retry_results = []
        retry_failed_tickers = []

        for ticker in final_failed_tickers:
            logging.info(f"Retrying: {ticker}")
            retry_result = get_stock_data(ticker)
            if retry_result is not None:
                retry_results.append(retry_result)
                logging.info(f"Successfully retrieved data for {ticker}")
            else:
                retry_failed_tickers.append(ticker)
                logging.warning(f"Failed to retrieve data for {ticker}")

            # 각 종목 처리 후 대기 시간을 둠
            time.sleep(random.uniform(60, 80))  # 60~80초 사이의 랜덤 시간 대기

        # 최종 재시도 결과를 저장
        if retry_results:
            retry_df = pd.DataFrame(retry_results)
            retry_df.to_csv('retry_successful_tickers.csv', index=False, encoding='utf-8-sig')
            logging.info("Retry successful tickers have been written to retry_successful_tickers.csv")

        if retry_failed_tickers:
            with open('retry_failed_tickers.json', 'w', encoding='utf-8') as json_file:
                json.dump(retry_failed_tickers, json_file, ensure_ascii=False, indent=4)
            logging.warning("Retry failed tickers have been written to retry_failed_tickers.json")
        
else:
    logging.error("No data available for the selected markets.")
