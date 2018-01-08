package zhidao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.HttpRequestUtil;

public class MainTest {
	public static void main(String[] args) throws UnsupportedEncodingException {

		String word = "深圳有什么机器人";
		word = URLEncoder.encode(word, "gbk");

		List<QuestionAnswer> list = zhidao(word);
		System.out.println(list.size());
	}

	private static List<QuestionAnswer> zhidao(String word) {
		List<QuestionAnswer> list = new ArrayList<QuestionAnswer>();
		String url = "https://zhidao.baidu.com/search?word=" + word;
		String response = HttpRequestUtil.sendGet(url, "gbk");
		System.out.println(response);
		if (response != null && response.length() > 0) {
			Document doc = Jsoup.parse(response);
			Elements elements = doc.select("div#wgt-list>dl");

			for (Element element : elements) {
				element = element.select("dt>a[href]").get(0);
				String href = element.attr("href").toString();
				try {
					doc = Jsoup.connect(href).get();
					QuestionAnswer questionAnswer = new QuestionAnswer();
					String question = doc.select("h1 span.ask-title").first().text();
					String answer = doc.select("div[class=line content]").first().child(0).text();
					questionAnswer.setQuestion(question);
					questionAnswer.setAnswer(answer);
					//// 控制台打印
					System.out.println(questionAnswer);
					list.add(questionAnswer);
				} catch (Exception e) {
					e.printStackTrace();
					doc = null;
					continue;
				}
			}
		}
		return list;
	}
}
