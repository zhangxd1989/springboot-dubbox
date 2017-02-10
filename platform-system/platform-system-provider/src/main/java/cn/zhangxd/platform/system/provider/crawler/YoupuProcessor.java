package cn.zhangxd.platform.system.provider.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class YoupuProcessor implements PageProcessor {

    private static final String COUNTRY_PAGE = "http://www.youpu.cn/Destination/strategy\\?id=\\d+";
    private static final String COUNTRY_EFFECT_PAGE = "http://www.youpu.cn/Destination/effect\\?id=\\d+";

    private Site site =
            Site.me()
                    .setCycleRetryTimes(3)
                    .setRetryTimes(3)
                    .setSleepTime(100)
                    .setTimeOut(3 * 60 * 1000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex(COUNTRY_PAGE).all());
        page.addTargetRequests(page.getHtml().links().regex(COUNTRY_EFFECT_PAGE).all());
        if (page.getUrl().regex(COUNTRY_EFFECT_PAGE).match()) {
            String countryName = page.getHtml().xpath("//div[@class='bgGray5']/div/h3[@class='fl f30 lh30 mt10']/text()").toString();
            page.putField("countryName", countryName);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}