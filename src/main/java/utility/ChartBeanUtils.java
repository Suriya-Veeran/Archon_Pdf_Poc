package utility;

import apache_echarts.beans.chart.*;
import apache_echarts.beans.html_beans.HtmlCreationInfoBean;
import apache_echarts.enums.FormatTypes;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ChartBeanUtils {


    public static SeriesInfoBean createSeriesInfoBean(List<DataInfoBean> dataInfoBeanList) {
        return SeriesInfoBean.builder()
                .data(dataInfoBeanList)
                .build();
    }

    public static  LegendInfoBean createLegendInfoBean(List<String> data) {
        return LegendInfoBean.builder().
                data(data)
                .build();
    }

    public static  DataInfoBean buildDataInfoBean(String name,
                                           int value,
                                           FormatTypes formatType,
                                           String color) {
        return DataInfoBean.builder()
                .name(name)
                .value(value)
                .format(formatType)
                .itemStyle(ItemStyle.builder().color(color).build())
                .build();
    }

    public static  HtmlCreationInfoBean createChartConfig(
            ChartBasicInfo chartBasicInfo,
            TitleInfoBean titleInfoBean,
            LegendInfoBean legendInfoBean,
            SeriesInfoBean seriesInfoBean) {

        return HtmlCreationInfoBean.builder()
                .chartBasicInfo(chartBasicInfo)
                .titleInfoBean(titleInfoBean)
                .legendInfoBean(legendInfoBean)
                .seriesInfoBean(seriesInfoBean)
                .build();
    }

    public static  TitleInfoBean createTitleConfig(String title,
                                            int fontSize,
                                            String fontFamily,
                                            String fontWeight,
                                            String color) {
        return TitleInfoBean.builder()
                .text(title)
                .textStyle(TextStyleBean.builder()
                        .fontSize(fontSize)
                        .fontFamily(fontFamily)
                        .fontWeight(fontWeight)
                        .color(color)
                        .build())
                .build();
    }

    public static  ChartBasicInfo createChartBasicInfo(String width,
                                                String height,
                                                String chartType) {
        return ChartBasicInfo.builder()
                .chartWidth(width)
                .chartHeight(height)
                .chartType(chartType)
                .build();

    }

}
