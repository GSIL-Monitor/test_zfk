package gkbCenter.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class BaiduService {
	private static final String ZHIDAO_URL = "https://zhidao.baidu.com/search?word=";
	private static final String BAIDU_URL = "https://www.baidu.com/s?wd=";
	private static final String BAIKE_URL = "https://baike.baidu.com/search?word=";
	
	/**
	 * 百度知道查询 方法用途: <br>
	 * 
	 * @param question
	 * @return
	 */
	public String getZhiDaoAnswer(String question) {
		System.out.println("问答：" + question);
		String url = ZHIDAO_URL + question;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements eles = doc.select(".content>.desc>p,.answer");
			if (eles.size() > 0) {
				return ((Element) eles.get(0)).text().replace("答：", "").replace("推荐答案", "").replace("[详细]", "");
			}

			return ((Element) doc.select(".desc>p").get(0)).text();
		} catch (Exception e) {
			System.out.println("抓取百度知道 " + url + " 错误");
		}
		return "";
	}

	/**
	 * 
	 * 百度知道详细 <br>
	 * 
	 * @param url
	 * @return
	 */
	public String getAnswerDetail(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements detail = doc.select(".best-text,.answer-text>span");
			if (detail.size() > 0) {
				return ((Element) detail.get(0)).text();
			}
		} catch (Exception e) {
			System.out.println("抓取知道详细 " + url + " 错误");
		}
		return "";
	}

	/**
	 * 百度网页查询 方法用途: <br>
	 * 
	 * @param question
	 * @return
	 */
	public String getWebAnswer(String question) {
		String url = BAIDU_URL + question;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements eles = doc.select(
					".op-constellation03-info,.op_dict_text2,.op_exrate_result,.op_mobilephone_r,.op_exactqa_family_s_answer,.op_kefupoly_table,.op_exactqa_s_answer,.op_exactqa_answer_s,.op_gdp_subtitle");

			if (eles.size() > 0) {
				return ((Element) eles.get(0)).text().replace(" ", "");
			}
		} catch (Exception e) {
			System.out.println("抓取百度网页 " + url + " 错误");
		}
		return getZhiDaoAnswer(question);
	}

	/**
	 * 百度百科查询 方法用途: <br>
	 * 
	 * @param question
	 * @return
	 */
	public String getBaiKeAnswer(String question) {
		String url = BAIKE_URL + question;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements eles = doc.select("dd>.result-title");
			url = ((Element) eles.get(0)).attr("href");
			url = "https://baike.baidu.com" + url;
			doc = Jsoup.connect(url).get();
			eles = doc.select(".para");
			return ((Element) eles.get(0)).text().replace("[1]", "");
		} catch (Exception e) {
			System.out.println("抓取百度百科 " + url + " 错误");
		}
		return getWebAnswer(question);
	}

	/**
	 * 百度网页查询，关于股票 方法用途: <br>
	 * 
	 * @param question
	 * @return
	 */
	public String getStockInfo(String question) {
		String url = BAIDU_URL + question;
		try {
			Document doc = Jsoup.connect(url).get();
			String answer = " 股价"
					+ ((Element) doc.select(".op-stockdynamic-moretab-cur-num,.op_stockweakdemand_cur_num").get(0))
							.text()
					+ "元";
			answer = answer + "  涨幅"
					+ ((Element) doc.select(".op-stockdynamic-moretab-cur-info,.op_stockweakdemand_cur_info").get(0))
							.text();
			return answer + "  更新时间："
					+ ((Element) doc.select(".op-stockdynamic-moretab-update,.op_stockweakdemand_update").get(0))
							.text();
		} catch (Exception e) {
			System.out.println("抓取股价 " + url + " 错误");
		}
		return getZhiDaoAnswer(question);
	}

	/**
	 * 百度网页查询，关于计算 方法用途: <br>
	 * 
	 * @param question
	 * @return
	 */
	public String getCounter(String question) {
		String url = BAIDU_URL + question;
		try {
			Document doc = Jsoup.connect(url).get();
			String answer = ((Element) ((Element) doc.select(".c-tool-counter").get(0)).parent().nextElementSibling()
					.children().get(1)).text().replace(" ", "");
			return "等于" + answer.substring(answer.lastIndexOf("=") + 1);
		} catch (Exception e) {
			System.out.println("抓取计算器 " + url + " 错误");
		}
		return getZhiDaoAnswer(question);
	}

	public String getAnswer(String question, String from) {
		int fromID = Integer.parseInt(from);
		switch (fromID) {
		case 10:
			return getWebAnswer(question);
		case 11:
			return getStockInfo(question);
		case 13:
			return getCounter(question);
		case 14:
			return getBaiKeAnswer(question);
		}
		return getZhiDaoAnswer(question);
	}
}
