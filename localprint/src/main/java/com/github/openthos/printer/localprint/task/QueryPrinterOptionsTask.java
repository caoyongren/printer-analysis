package com.github.openthos.printer.localprint.task;

import com.github.openthos.printer.localprint.model.PrinterOptionItem;

import java.util.List;

/**
 * Created by bboxh on 2016/5/31.
 */
public class QueryPrinterOptionsTask<Progress> extends  CommandTask<String, Progress, PrinterOptionItem>  {
    @Override
    protected String[] setCmd(String... params) {

        String printerName = params[0];

        return new String[]{};
    }

    @Override
    protected PrinterOptionItem handleCommand(List<String> stdOut, List<String> stdErr) {

        // TODO: 2016/6/1 查询打印机设置 B10
        
        PrinterOptionItem item = new PrinterOptionItem();
        for(String line:stdOut){
            String[] firstSplit = line.split("/");
            boolean colorIsExist = false;
            boolean sizeIsExist = false;

            if(firstSplit[0].equals("ColorModel") || firstSplit[0].equals("Color") || firstSplit[0].equals("ColorMode") ) {
                colorIsExist = true;
                item.setColorModeName(firstSplit[0]);

                String[] secondSplit = firstSplit[1].split(": ");
                String[] thirdSplit = secondSplit[1].split(" ");
                for (int i = 0; i < thirdSplit.length; i++) {
                    if (thirdSplit[i].startsWith("*")) {
                        thirdSplit[i] = thirdSplit[i].replace("*", "");
                        item.addColorModeItem(thirdSplit[i], true);
                    } else
                        item.addColorModeItem(thirdSplit[i], false);
                }
            }
            if (!colorIsExist)
                item.setColorModeName(null);



            if(firstSplit[0].equals("PageSize")) {
                sizeIsExist = true;
                item.setMediaSizeName(firstSplit[0]);

                String[] secondSplit = firstSplit[1].split(": ");
                String[] thirdSplit = secondSplit[1].split(" ");
                for (int i = 0; i < thirdSplit.length; i++) {
                    if (thirdSplit[i].startsWith("*")) {
                        thirdSplit[i] = thirdSplit[i].replace("*", "");
                        item.addMediaSizeItem(thirdSplit[i], true);
                    } else
                        item.addMediaSizeItem(thirdSplit[i], false);
                }
            }
            if(!sizeIsExist)
                item.setMediaSizeName(null);

        }
        //模拟数据
        /*
        item.setColorModeName("ColorMode");
        item.setMediaSizeName("PageSize");
        item.addColorModeItem("Color", true);
        item.addColorModeItem("Grayscale", true);
        item.addMediaSizeItem("Letter", false);
        item.addMediaSizeItem("A4", true);
        */
        return item;
    }

    @Override
    protected String bindTAG() {
        return "QueryPrinterOptionsTask";
    }
}