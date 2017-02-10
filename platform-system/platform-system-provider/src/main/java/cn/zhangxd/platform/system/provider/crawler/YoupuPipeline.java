package cn.zhangxd.platform.system.provider.crawler;

import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Objects;

@Repository
public class YoupuPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        resultItems.getAll().entrySet().stream().filter(entry -> "countryName".equals(entry.getKey())).forEach(entry -> {
            String countryName = Objects.toString(entry.getValue());
            System.out.println(countryName);
        });
    }
}
