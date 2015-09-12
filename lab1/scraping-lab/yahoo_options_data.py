from bs4 import BeautifulSoup
import json
import re


def curr_price(soup):
    return float(soup.find('span', id=re.compile(r'^yfs_l84_.*$')).text)


def date_urls(soup):
    date_urls = soup.find_all('a',href=re.compile(r'^/q/op\?s=\w+&m=\d{4}-\d{2}$'))
    date_urls = map(lambda url: url['href'], date_urls)
    last_date_url = soup.find('a', href=re.compile(r'^/q/os\?s=\w+&m=\d{4}-\d{2}-\d{2}$'))['href']
    date_urls.append(last_date_url)
    return map(lambda url: "http://finance.yahoo.com%s" %
               url.replace('&', '&amp;'), date_urls)


def option_quote(option):
    data = re.match(r"(?P<symbol>[a-zA-Z]+\d?)(?P<date>\d{6})(?P<type>[a-zA-Z]{1}).*", option[1], re.UNICODE)
    return {
        "Ask": option[5],
        "Bid": option[4],
        "Change": option[3],
        "Date": data.group("date"),
        "Last": option[2],
        "Open": option[7],
        "Strike": option[0],
        "Symbol": data.group("symbol"),
        "Type": data.group("type"),
        "Vol": option[6]
    }


def option_quotes(soup):
    option_tables = soup.find_all('table', attrs={'class': re.compile(r'^yfnc_datamodoutline1$')})
    option_rows = map(lambda table: table.findChildren('tr')[2:], option_tables)
    option_cells = [[td.text for td in row.findChildren('td')] for rows in option_rows for row in rows]
    quotes = map(option_quote, option_cells)
    return sorted(quotes, key=lambda k: float(k['Open'].replace(',', '')), reverse=True)


def contractAsJson(filename):
    html_doc = '\n'.join(open(filename).readlines())
    soup = BeautifulSoup(html_doc, 'html.parser')
    contract = {
        "currPrice": curr_price(soup),
        "dateUrls": date_urls(soup),
        "optionQuotes": option_quotes(soup)
    }
    jsonQuoteData = json.dumps(contract, sort_keys=True, indent=4, separators=(',', ': '))
    return jsonQuoteData
