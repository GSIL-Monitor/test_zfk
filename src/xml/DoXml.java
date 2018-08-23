package xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DoXml
{
    /**
     * 拼装请求历史告警xml
     * 
     * @param map
     * @return String
     */
    public static String requestAlarmXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_History_Alarm");
        Element item = doc.createElement("Item");
        item.setAttribute("code", map.get("code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("Type", map.get("Type"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        item.setAttribute("Level", map.get("Level"));
        item.setAttribute("FromIndex", map.get("FromIndex"));
        item.setAttribute("ToIndex", map.get("ToIndex"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 文件上传下载
     * 
     * @param map
     * @return String
     */
    public static String requestUploadXml()
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_File_Upload");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    // <?xml version="1.0" encoding="UTF-8"?>
    // <HTTP_XML EventType="Request_File_List" RequestId="请求ID"
    // TransactionId="事务ID">
    // <Item FileType="文件类型" FromSource="起始来源类型" ToSource="结束来源类型"
    // BeginTime="起始时间" EndTime="结束时间" FromIndex="起始记录数" ToIndex="结束记录数"/>
    // </HTTP_XML>
    /**
     * 文件检索(客户端与集中存储之间)
     * 
     * @param map
     * @return String
     */
    public static String requestSearchXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_File_List");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("FileType", map.get("FileType"));
        item.setAttribute("FromSource", map.get("FromSource"));
        item.setAttribute("ToSource", map.get("ToSource"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        item.setAttribute("Level", map.get("Level"));
        item.setAttribute("FromIndex", map.get("FromIndex"));
        item.setAttribute("ToIndex", map.get("ToIndex"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 文件添加删除(客户端与集中存储之间)
     * 
     * @param map
     * @return String
     */
    // <?xml version="1.0" encoding="UTF-8"?>
    // <HTTP_XML EventType="Request_Add_File" RequestId="请求ID"
    // TransactionId="事务ID">
    // <Item FileName="文件名" FileType="非录像文件" FilePath="文件路径" Size="文件大小(字节)"
    // Time="文件创建时间"/>
    // <Item FileName="文件名" FileType="录像文件" FilePath="文件路径" Size="文件大小(字节)"
    // Time="文件创建时间" DevCode="设备编码"
    // BeginRecordTime="开始录像时间" EndRecordTime="结束录像时间" DecoderTag="解码标签"
    // RecordType="录像类型" sps="sps头信息"/>
    // </HTTP_XML>

    public static String requestFileXml(List<Map<String, String>> list)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Add_File");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        for (int i = 0; i < list.size(); i++)
        {
            Map<String, String> map = new HashMap<String, String>();
            map = list.get(i);
            Element item = doc.createElement("Item");
            if ("2".equals(map.get("FileType")))
            {
                item.setAttribute("FileName", map.get("FileName"));
                item.setAttribute("FileType", map.get("FileType"));
                item.setAttribute("FilePath", map.get("FilePath"));
                item.setAttribute("Size", map.get("Size"));
                item.setAttribute("Time", map.get("Time"));
            } else
            {
                item.setAttribute("FileName", map.get("FileName"));
                item.setAttribute("FileType", map.get("FileType"));
                item.setAttribute("FilePath", map.get("FilePath"));
                item.setAttribute("Size", map.get("Size"));
                item.setAttribute("Time", map.get("Time"));
                item.setAttribute("DevCode", map.get("DevCode"));
                item.setAttribute("BeginRecordTime", map.get("BeginRecordTime"));
                item.setAttribute("EndRecordTime", map.get("EndRecordTime"));
                item.setAttribute("DecoderTag", map.get("DecoderTag"));
                item.setAttribute("RecordType", map.get("RecordType"));
                item.setAttribute("sps", map.get("sps"));
            }
            head.appendChild(item);
        }
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 组装遗留物检测的xml
     */
    public static String ylwjcXml(List<Map<String, String>> list)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("Message");
        head.setAttribute("EventType", "Request_SetVCAParam");
        head.setAttribute("ClientId", "137");
        Map<String, String> map = new HashMap<String, String>();
        map = list.get(0);
        // 创建Item
        Element item = doc.createElement("Item");
        item.setAttribute("Code", map.get("Code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("Enable", map.get("Enable"));
        item.setAttribute("Type", map.get("Type"));
        item.setAttribute("ConfigID", map.get("ConfigID"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        map.clear();
        map = list.get(1);
        // 创建AnalysisParam
        Element analysisParam = doc.createElement("AnalysisParam");
        Element regionList = doc.createElement("RegionList");
        regionList.setAttribute("Duration", map.get("Duration"));
        regionList.setAttribute("Height", map.get("Height"));
        regionList.setAttribute("Width", map.get("Width"));
        analysisParam.appendChild(regionList);
        item.appendChild(analysisParam);
        // 创建Schedule
        map.clear();
        map = list.get(2);
        Element schedule = doc.createElement("Schedule");
        schedule.setAttribute("WeekNum", map.get("WeekNum"));
        int i = 0;
        int j = 0;
        int a = 0;
        int weekNum = Integer.parseInt(map.get("WeekNum"));
        for (i = 0; i < weekNum; i++)
        {
            map.clear();
            map = list.get(3 + a);
            Element weekday = doc.createElement("Weekday");
            weekday.setAttribute("Value", map.get("Value"));
            weekday.setAttribute("TimeNum", map.get("TimeNum"));
            int timeNum = Integer.parseInt(map.get("TimeNum"));
            a++;
            for (j = 0; j < timeNum; j++)
            {
                map.clear();
                map = list.get(3 + a);
                Element time = doc.createElement("Time");
                time.setAttribute("Begin", map.get("Begin"));
                time.setAttribute("End", map.get("End"));
                weekday.appendChild(time);
                a++;
            }
            schedule.appendChild(weekday);
        }
        item.appendChild(schedule);
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 组装徘徊的xml
     */
    public static String phXml(List<Map<String, String>> list)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("Message");
        head.setAttribute("EventType", "Request_SetVCAParam");
        head.setAttribute("ClientId", "137");
        Map<String, String> map = new HashMap<String, String>();

        map = list.get(0);
        // 创建Item
        Element item = doc.createElement("Item");
        item.setAttribute("Code", map.get("Code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("Enable", map.get("Enable"));
        item.setAttribute("Type", map.get("Type"));
        item.setAttribute("ConfigID", map.get("ConfigID"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        map.clear();
        map = list.get(1);
        // 创建AnalysisParam
        Element analysisParam = doc.createElement("AnalysisParam");
        Element regionList = doc.createElement("RegionList");
        regionList.setAttribute("RegionNum", map.get("RegionNum"));
        regionList.setAttribute("Sensitivity", map.get("Sensitivity"));
        regionList.setAttribute("Duration", map.get("Duration"));
        regionList.setAttribute("Height", map.get("Height"));
        regionList.setAttribute("Width", map.get("Width"));

        int i = 0;
        int j = 0;
        int a = 0;
        int regionNum = Integer.parseInt(map.get("RegionNum"));
        for (i = 0; i < regionNum; i++)
        {
            Element region = doc.createElement("Region");
            map.clear();
            map = list.get(2 + a);
            region.setAttribute("PointNum", map.get("PointNum"));
            int pointNum = Integer.parseInt(map.get("PointNum"));
            a++;
            for (j = 0; j < pointNum; j++)
            {
                map.clear();
                map = list.get(2 + a);
                Element point = doc.createElement("Point");
                point.setAttribute("X", map.get("X"));
                point.setAttribute("Y", map.get("Y"));
                region.appendChild(point);
                a++;
            }
            regionList.appendChild(region);
        }
        analysisParam.appendChild(regionList);
        item.appendChild(analysisParam);
        // 创建Schedule
        map.clear();
        map = list.get(2 + a);
        Element schedule = doc.createElement("Schedule");
        schedule.setAttribute("WeekNum", map.get("WeekNum"));
        int m = 0;
        int n = 0;
        int weekNum = Integer.parseInt(map.get("WeekNum"));
        for (m = 0; m < weekNum; m++)
        {
            map.clear();
            map = list.get(3 + a);
            Element weekday = doc.createElement("Weekday");
            weekday.setAttribute("Value", map.get("Value"));
            weekday.setAttribute("TimeNum", map.get("TimeNum"));
            int timeNum = Integer.parseInt(map.get("TimeNum"));
            a++;
            for (n = 0; n < timeNum; n++)
            {
                map.clear();
                map = list.get(3 + a);
                Element time = doc.createElement("Time");
                time.setAttribute("Begin", map.get("Begin"));
                time.setAttribute("End", map.get("End"));
                weekday.appendChild(time);
                a++;
            }
            schedule.appendChild(weekday);
        }
        item.appendChild(schedule);
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 组装烟火检测的xml
     */
    public static String yhjcXml(List<Map<String, String>> list)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("Message");
        head.setAttribute("EventType", "Request_SetVCAParam");
        head.setAttribute("ClientId", "137");
        Map<String, String> map = new HashMap<String, String>();
        map = list.get(0);
        // 创建Item
        Element item = doc.createElement("Item");
        item.setAttribute("Code", map.get("Code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("Enable", map.get("Enable"));
        item.setAttribute("Type", map.get("Type"));
        item.setAttribute("ConfigID", map.get("ConfigID"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        map.clear();
        map = list.get(1);
        // 创建AnalysisParam
        Element analysisParam = doc.createElement("AnalysisParam");
        Element regionList = doc.createElement("RegionList");
        regionList.setAttribute("RedThreshold", map.get("RedThreshold"));
        regionList.setAttribute("Saturation", map.get("Saturation"));
        regionList.setAttribute("Height", map.get("Height"));
        regionList.setAttribute("Width", map.get("Width"));
        analysisParam.appendChild(regionList);
        item.appendChild(analysisParam);
        // 创建Schedule
        map.clear();
        map = list.get(2);
        Element schedule = doc.createElement("Schedule");
        schedule.setAttribute("WeekNum", map.get("WeekNum"));
        int i = 0;
        int j = 0;
        int a = 0;
        int weekNum = Integer.parseInt(map.get("WeekNum"));
        for (i = 0; i < weekNum; i++)
        {
            map.clear();
            map = list.get(3 + a);
            Element weekday = doc.createElement("Weekday");
            weekday.setAttribute("Value", map.get("Value"));
            weekday.setAttribute("TimeNum", map.get("TimeNum"));
            int timeNum = Integer.parseInt(map.get("TimeNum"));
            a++;
            for (j = 0; j < timeNum; j++)
            {
                map.clear();
                map = list.get(3 + a);
                Element time = doc.createElement("Time");
                time.setAttribute("Begin", map.get("Begin"));
                time.setAttribute("End", map.get("End"));
                weekday.appendChild(time);
                a++;
            }
            schedule.appendChild(weekday);
        }
        item.appendChild(schedule);
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 开始下载
     * 
     * @return
     */
    public static String startDownloadXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Start_DownLoad");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("Code", map.get("Code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("DecoderTag", map.get("DecoderTag"));
        item.setAttribute("BeginTime", map.get("BeginTime"));
        item.setAttribute("EndTime", map.get("EndTime"));
        item.setAttribute("Type", map.get("Type"));
        item.setAttribute("Size", map.get("Size"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 取消下载
     * 
     * @return
     */
    public static String cancelDownloadXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Cancel_DownLoad");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("WorkID", map.get("WorkID"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 停止下载
     * 
     * @return
     */
    public static String stopDownloadXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Stop_DownLoad");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("WorkID", map.get("WorkID"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 查询下载状态
     * 
     * @return
     */
    public static String statusDownloadXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Start_DownLoad");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        for (int i = 0; i < map.size(); i++)
        {
            Element item = doc.createElement("Item");
            item.setAttribute("WorkID", map.get("WorkID"));
            head.appendChild(item);
        }
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 开始录像
     * 
     * @return
     */
    public static String startRecordXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Start_Record");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("Code", map.get("Code"));
        item.setAttribute("UserCode", map.get("UserCode"));
        item.setAttribute("DecoderTag", map.get("DecoderTag"));
        item.setAttribute("TimeDuration", map.get("TimeDuration"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 停止录像
     * 
     * @return
     */
    public static String stopRecordXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Stop_Record");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 取消录像
     * 
     * @return
     */
    public static String cancelRecordXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Cancel_Record");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("WorkID", map.get("WorkID"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 重置录像时长
     * 
     * @return
     */
    public static String continueRecordXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Continue_Record");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        Element item = doc.createElement("Item");
        item.setAttribute("WorkID", map.get("WorkID"));
        item.setAttribute("TimeDuration", map.get("TimeDuration"));
        head.appendChild(item);
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    /**
     * 查询录像状态
     * 
     * @return
     */
    public static String statusRecordXml(HashMap<String, String> map)
    {
        Document doc = XmlUtil.newXMLDocument();
        Element head = doc.createElement("HTTP_XML");
        head.setAttribute("EventType", "Request_Record_Status");
        head.setAttribute("RequestId", "2015");
        head.setAttribute("TransactionId", "2015");
        for (int i = 0; i < map.size(); i++)
        {
            Element item = doc.createElement("Item");
            item.setAttribute("WorkID", map.get("WorkID"));
            head.appendChild(item);
        }
        doc.appendChild(head);
        String str = XmlUtil.toStringFromDoc(doc);
        return str;
    }

    // HTTP响应码
    // 状态码 描述
    // 200 下载任务添加成功
    // 400 请求消息格式错误，或者任务标识已存在
    // 403 无权限，请求被禁止
    // 404 未找到指定目标
    // 500 服务内部错误，如磁盘满或访问数据库失败等
    // 503 服务繁忙

}
