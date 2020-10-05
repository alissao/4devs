package com.olivia.devs.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.FileWriter;

@Component
public class BaseCrawler {

    public BaseCrawler() {
    }

    public FileWriter getRandomNickCpf(String fileName) {

        try {
            HtmlPage forDevs = new WebClient(BrowserVersion.BEST_SUPPORTED)
                    .getPage("https://www.4devs.com.br/gerador_de_nicks");
            DomElement focusElement = forDevs.getElementById("method");
            focusElement.setAttribute("value", "random");
            focusElement = forDevs.getElementById("quantity");
            focusElement.setAttribute("value", "50");
            focusElement = forDevs.getElementById("limit");
            focusElement.setAttribute("value", "8");
            focusElement = forDevs.getElementById("bt_gerar_nick");
            focusElement.click();

            //2. Fetch the HTML code
            Document document = Jsoup.parse(forDevs.getElementById("area_resposta").toString());
            //3. Parse the HTML to extract links to other URLs
            Elements nicksOnPage = document.select("generated-nick");

            FileWriter txt = new FileWriter(fileName);

            System.out.println("File created: " + fileName);
            //5. For each extracted URL... go back to Step 4.
            for (Element page : nicksOnPage) {
                txt.write(page.ownText() + ";" + getRandomCpf() + String.format("%n"));
            }
            System.out.println("File completed.");
            return txt;
        } catch (Exception e) {
            System.err.println("For 'nick': " + e.getMessage());
            return null;
        }
    }

    private String getRandomCpf() {
        String randomCpf = new String();
        try {
            HtmlPage forDevs = new WebClient(BrowserVersion.BEST_SUPPORTED)
                    .getPage("https://www.4devs.com.br/gerador_de_cpf");
            DomElement focusElement = forDevs.getElementById("bt_gerar_cpf");
            focusElement.click();

            //2. Fetch the HTML code
            Document document = Jsoup.parse(forDevs.getElementById("texto_cpf").toString());

            return document.ownText();
        } catch (Exception e) {
            System.err.println("For 'cpf': " + e.getMessage());
            return randomCpf;
        }
    }

}
