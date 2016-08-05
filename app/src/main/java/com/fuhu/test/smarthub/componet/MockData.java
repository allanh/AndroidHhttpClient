package com.fuhu.test.smarthub.componet;


import android.os.Bundle;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.GSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MockData {
    private static final String TAG = MockData.class.getSimpleName();

    public static  String Str_ChannelListMockData = "{" +
                                                "\"status\":\"0\", " +
                                                "\"message\":\"ok\", " +
                                                "\"channels\":[" +
                                                                "{" +
                                                                    "\"channelID\":\"channel001\",   " +
                                                                    "\"channelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\",   " +
                                                                    "\"channelTitle\":\"Channel 1\",\n" +
                                                                    "\"channelDescrip\":\"Channel 1 discription.\",\n" +
                                                                    "\"arraylistTrigger\":[" +
                                                                    "{\"triggerActionID\":\"trig1\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel1 - Trigger1\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                    "{\"triggerActionID\":\"trig2\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel1 - Trigger2\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                    "{\"triggerActionID\":\"trig3\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel1 - Trigger3\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}" +
                                                                    "]," +
                                                                    "\"arraylistAction\":[" +
                                                                                        "{\"triggerActionID\":\"act1\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel1 - Action1\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                        "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                        "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                        "]}," +
                                                                                        "{\"triggerActionID\":\"act2\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel1 - Action2\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                        "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                        "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                        "]}," +
                                                                                        "{\"triggerActionID\":\"act3\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel1 - Action3\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                        "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                        "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                        "]}" +
                                                                    "] " +
                                                                "}, " +
                                                                "{" +
                                                                    "\"channelID\":\"channel002\",   " +
                                                                    "\"channelUrlPath\":\"http://i.imgur.com/nDbTsmW.png\",   " +
                                                                    "\"channelTitle\":\"Channel 2\",\n" +
                                                                    "\"channelDescrip\":\"Channel 2 discription.\",\n" +
                                                                    "\"arraylistTrigger\":[" +
                                                                    "{\"triggerActionID\":\"trig1\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel2 - Trigger1\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                    "{\"triggerActionID\":\"trig2\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel2 - Trigger2\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                    "{\"triggerActionID\":\"trig3\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel2 - Trigger3\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Detect baby crying\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}" +
                                                                    "]," +
                                                                    "\"arraylistAction\":[" +
                                                                    "{\"triggerActionID\":\"act1\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel2 - Action1\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                    "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                    "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                    "]}," +
                                                                    "{\"triggerActionID\":\"act2\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel2 - Action2\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                    "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                    "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                    "]}," +
                                                                    "{\"triggerActionID\":\"act3\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel2 - Action3\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                    "{\"fieldName\":\"Music Select\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                    "{\"fieldName\":\"Playing Time Select\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "} " +
                                                                    "]}" +
                                                                    "] " +
                                                                "}, " +
                                                                "{" +
                                                                    "\"channelID\":\"channel003\",   " +
                                                                    "\"channelUrlPath\":\"http://i.imgur.com/LWdp8KA.png\",   " +
                                                                    "\"channelTitle\":\"Channel 3\",\n" +
                                                                    "\"channelDescrip\":\"Channel 3 discription.\",\n" +
                                                                    "\"arraylistTrigger\":[" +
                                                                                        "{\"triggerActionID\":\"trig1\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel3 - Trigger1\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                                        "{\"triggerActionID\":\"trig2\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel3 - Trigger2\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}," +
                                                                                        "{\"triggerActionID\":\"trig3\",\"triggerActionName\":\"Baby Monitor\",\"triggerActionType\":\"trigger\",\"triggerActionDescrip\":\"Channel3 - Trigger3\",\"isAction\":\"false\",\"arraylistTriggerActionField\":[{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\" }]}" +
                                                                                      "]," +
                                                                    "\"arraylistAction\":[" +
                                                                                        "{\"triggerActionID\":\"act1\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel3 - Action1\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                                                                                                                                                     "]}," +
                                                                                        "{\"triggerActionID\":\"act2\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel3 - Action2\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                                                                                                                                                    "]}," +
                                                                                        "{\"triggerActionID\":\"act3\",\"triggerActionName\":\"Lullaby/White Noise\",\"triggerActionType\":\"action\",\"triggerActionDescrip\":\"Channel3 - Action3\",\"isAction\":\"true\",\"arraylistTriggerActionField\":[" +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                                                                                                                                                        "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                                                                                                                                                     "]}" +
                                                                                      "] " +
                                                                "}" +
                                                            "]" +
                                             "}";


    public static String Str_TriggerActionItemListMockData ="{" +
                                                        "\"status\":\"0\", " +
                                                        "\"message\":\"ok\", " +
                                                        "\"triggerActions\":[" +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"trig1\"," +
                                                                                    "\"triggerActionType\":\"trigger\"," +
                                                                                    "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act1\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - Action1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act1\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - Action1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                "\"triggerActionID\":\"act1\"," +
                                                                                "\"triggerActionType\":\"action\"," +
                                                                                "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                                "\"triggerActionDescrip\":\"Channel1 - Action1\"," +
                                                                                "\"arraylistTriggerActionField\":[" +
                                                                                                                "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                            "]" +
                                                                                "}" +


                                                                         "]" +
                                                   "}";



    public static String Str_TriggerActionItemListMockData_GoodNightMode ="{" +
                                                                    "\"status\":\"0\", " +
                                                                    "\"message\":\"ok\", " +
                                                                    "\"triggerActions\":[" +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"trig1\"," +
                                                                                            "\"triggerActionType\":\"trigger\"," +
                                                                                            "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                            "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act1\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Night Light\"," +
                                                                                            "\"triggerActionDescrip\":\"Turn on Night Light\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act2\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                                            "\"triggerActionDescrip\":\"Play Lullaby/White Noise\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act3\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                            "\"triggerActionDescrip\":\"Turn on Baby Monitor\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}" +
                                                                    "]" +
                                                                "}";

    public static String Str_TriggerActionItemListMockData_GoodMorningMode ="{" +
                                                                    "\"status\":\"0\", " +
                                                                    "\"message\":\"ok\", " +
                                                                    "\"triggerActions\":[" +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"trig1\"," +
                                                                                            "\"triggerActionType\":\"trigger\"," +
                                                                                            "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                            "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act1\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Sleep Tracker\"," +
                                                                                            "\"triggerActionDescrip\":\"End Sleep Tracker\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act2\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Night Light\"," +
                                                                                            "\"triggerActionDescrip\":\"Turn off Night Light\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act3\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Play a station\"," +
                                                                                            "\"triggerActionDescrip\":\"Play a station from iHeart family\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}," +
                                                                                        "{" +
                                                                                            "\"triggerActionID\":\"act4\"," +
                                                                                            "\"triggerActionType\":\"action\"," +
                                                                                            "\"triggerActionName\":\"Baby monitor\"," +
                                                                                            "\"triggerActionDescrip\":\"Baby monitor on standby\"," +
                                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                                            "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                        "]" +
                                                                                        "}" +
                                                                                    "]" +
                                                                    "}";

    public static String Str_TriggerActionItemListMockData_NapTime ="{" +
                                                            "\"status\":\"0\", " +
                                                            "\"message\":\"ok\", " +
                                                            "\"triggerActions\":[" +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"trig1\"," +
                                                                                    "\"triggerActionType\":\"trigger\"," +
                                                                                    "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act1\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Sleep Tracker\"," +
                                                                                    "\"triggerActionDescrip\":\"Start Sleep Tracker\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act2\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                                    "\"triggerActionDescrip\":\"Play Lullaby/White Noise\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}" +
                                                                            "]" +
                                                            "}";

    public static String Str_TriggerActionItemListMockData_Nursing ="{" +
                                                            "\"status\":\"0\", " +
                                                            "\"message\":\"ok\", " +
                                                            "\"triggerActions\":[" +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"trig1\"," +
                                                                                    "\"triggerActionType\":\"trigger\"," +
                                                                                    "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act1\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Nursing Tracker\"," +
                                                                                    "\"triggerActionDescrip\":\"Start Nursing Tracker\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act2\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Night Light\"," +
                                                                                    "\"triggerActionDescrip\":\"Turn on Night Light\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}" +
                                                                            "]" +
                                                            "}";



    public static String Str_TriggerActionItemListMockData_Playtime ="{" +
                                                            "\"status\":\"0\", " +
                                                            "\"message\":\"ok\", " +
                                                            "\"triggerActions\":[" +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"trig1\"," +
                                                                                    "\"triggerActionType\":\"trigger\"," +
                                                                                    "\"triggerActionName\":\"Baby Monitor\"," +
                                                                                    "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}," +
                                                                                "{" +
                                                                                    "\"triggerActionID\":\"act1\"," +
                                                                                    "\"triggerActionType\":\"action\"," +
                                                                                    "\"triggerActionName\":\"Play music\"," +
                                                                                    "\"triggerActionDescrip\":\"Play music from iHeart Radio\"," +
                                                                                    "\"arraylistTriggerActionField\":[" +
                                                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                                "]" +
                                                                                "}" +
                                                                            "]" +
                                                            "}";



















    public static String Str_TriggerItemMockData = "{" +
                                                "\"status\":\"0\", " +
                                                "\"message\":\"ok\", " +
                                                "\"triggerActionID\":\"trig1\"," +
                                                "\"triggerActionType\":\"trigger\"," +
                                                "\"triggerActionName\":\"Baby Monitor\"," +
                                                "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                "\"arraylistTriggerActionField\":[" +
                                                                                "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                            "]" +
                                            "}";

    public static String Str_ActionItemMockData = "{" +
                                                    "\"status\":\"0\", " +
                                                    "\"message\":\"ok\", " +
                                                    "\"triggerActionID\":\"trig1\"," +
                                                    "\"triggerActionType\":\"action\"," +
                                                    "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                    "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                    "\"arraylistTriggerActionField\":[" +
                                                                                    "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                    "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                "]" +
                                                 "}";



    public static String mockRecipeItem_GoodNightMode = "\"userID\":\"00001\"," +
                                                  "\"recipeID\":\"00002\"," +
                                                  "\"recipeType\":\"Good Night Mode\"," +
                                                  "\"recipeDescrip\":\"Say Good Night to Pando and trigger multiple actions.\"," +
                                                  "\"recipeCreateTime\":\"Apr 18, 2016\"," +
                                                  "\"recipeRunTimes\":\"5\"," +
                                                  "\"recipeLastRunTime\":\"April 25, 2016\"," +
                                                  "\"thisChannelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\"," +
                                                  "\"thisChannelTitle\":\"original1\"," +
                                                  "\"thisChannelDescrip\":\"Thischannelis...\"," +
                                                  "\"triggerDescrip\":\"Tigger\"," +
                                                  "\"arraylistThatChannelUrl\":[\"http://i.imgur.com/0wM5es6.png\",\"http://i.imgur.com/sSjffRs.png\",\"http://i.imgur.com/xiZTTSV.png\"]," +
                                                  "\"arraylistThatChannelTitle\":[\"channel1\",\"channel2\",\"channel3\"]," +
                                                  "\"arraylistThatChannelDescrip\":[\"Thischannelis...\",\"Thischannelis...\",\"Thischannelis...\"]," +
                                                  "\"arraylistActionDescrip\":[\"Turn on Night Light\",\"Play Lullaby/White Noise\",\"Turn on Baby Monitor\"]," +
                                                  "\"isEnableNotificationFlag\":true," +
                                                  "\"isEnableUseFlag\":true";

    public static String mockRecipeItem_GoodMorningMode = "\"userID\":\"00001\"," +
                                                    "\"recipeID\":\"00003\"," +
                                                    "\"recipeType\":\"Good Morning Mode\"," +
                                                    "\"recipeDescrip\":\"Say Good Morning to Pando and trigger multiple actions.\"," +
                                                    "\"recipeCreateTime\":\"Apr 18, 2016\"," +
                                                    "\"recipeRunTimes\":\"5\"," +
                                                    "\"recipeLastRunTime\":\"April 25, 2016\"," +
                                                    "\"thisChannelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\"," +
                                                    "\"thisChannelTitle\":\"original1\"," +
                                                    "\"thisChannelDescrip\":\"Thischannelis...\"," +
                                                    "\"triggerDescrip\":\"Tigger\"," +
                                                    "\"arraylistThatChannelUrl\":[\"http://i.imgur.com/7oI2qiP.png\",\"http://i.imgur.com/0wM5es6.png\",\"http://i.imgur.com/3hyC3Bz.png\",\"http://i.imgur.com/xiZTTSV.png\"]," +
                                                    "\"arraylistThatChannelTitle\":[\"channel1\",\"channel2\",\"channel3\",\"channel4\"]," +
                                                    "\"arraylistThatChannelDescrip\":[\"Thischannelis...\",\"Thischannelis...\",\"Thischannelis...\",\"Thischannelis...\"]," +
                                                    "\"arraylistActionDescrip\":[\"End Sleep Tracker\",\"Turn off Night Light\",\"Play a station from iHeart family\",\"Baby monitor on standby\"]," +
                                                    "\"isEnableNotificationFlag\":true," +
                                                    "\"isEnableUseFlag\":true";

    public static String mockRecipeItem_NapTime = "\"userID\":\"00001\"," +
                                            "\"recipeID\":\"00004\"," +
                                            "\"recipeType\":\"Nap Time\"," +
                                            "\"recipeDescrip\":\"Say Baby is going to take a nap to Pando to trigger following actions\"," +
                                            "\"recipeCreateTime\":\"Apr 18, 2016\"," +
                                            "\"recipeRunTimes\":\"5\"," +
                                            "\"recipeLastRunTime\":\"April 25, 2016\"," +
                                            "\"thisChannelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\"," +
                                            "\"thisChannelTitle\":\"original1\"," +
                                            "\"thisChannelDescrip\":\"Thischannelis...\"," +
                                            "\"triggerDescrip\":\"Tigger\"," +
                                            "\"arraylistThatChannelUrl\":[\"http://i.imgur.com/7oI2qiP.png\",\"http://i.imgur.com/sSjffRs.png\"]," +
                                            "\"arraylistThatChannelTitle\":[\"channel1\",\"channel2\"]," +
                                            "\"arraylistThatChannelDescrip\":[\"Thischannelis...\",\"Thischannelis...\"]," +
                                            "\"arraylistActionDescrip\":[\"Start Sleep Tracker\",\"Play Lullaby/White Noise\"]," +
                                            "\"isEnableNotificationFlag\":true," +
                                            "\"isEnableUseFlag\":true";

    public static String mockRecipeItem_Nursing = "\"userID\":\"00001\"," +
                                            "\"recipeID\":\"00005\"," +
                                            "\"recipeType\":\"Nursing\"," +
                                            "\"recipeDescrip\":\"Say I'm nursing the baby to trigger the following actions\"," +
                                            "\"recipeCreateTime\":\"Apr 18, 2016\"," +
                                            "\"recipeRunTimes\":\"5\"," +
                                            "\"recipeLastRunTime\":\"April 25, 2016\"," +
                                            "\"thisChannelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\"," +
                                            "\"thisChannelTitle\":\"original1\"," +
                                            "\"thisChannelDescrip\":\"Thischannelis...\"," +
                                            "\"triggerDescrip\":\"Tigger\"," +
                                            "\"arraylistThatChannelUrl\":[\"http://i.imgur.com/7oI2qiP.png\",\"http://i.imgur.com/0wM5es6.png\"]," +
                                            "\"arraylistThatChannelTitle\":[\"channel1\",\"channel2\"]," +
                                            "\"arraylistThatChannelDescrip\":[\"Thischannelis...\",\"Thischannelis...\"]," +
                                            "\"arraylistActionDescrip\":[\"Start Nursing Tracker\",\"Turn on Night Light\"]," +
                                            "\"isEnableNotificationFlag\":true," +
                                            "\"isEnableUseFlag\":true";

    public static String mockRecipeItem_Playtime = "\"userID\":\"00001\"," +
                                            "\"recipeID\":\"00005\"," +
                                            "\"recipeType\":\"Playtime\"," +
                                            "\"recipeDescrip\":\"If user say Playtime (or other terms of similar meaning custonized by user), Pando to play music from iHeart Radio.\"," +
                                            "\"recipeCreateTime\":\"Apr 18, 2016\"," +
                                            "\"recipeRunTimes\":\"5\"," +
                                            "\"recipeLastRunTime\":\"April 25, 2016\"," +
                                            "\"thisChannelUrlPath\":\"http://i.imgur.com/F5XDgKk.png\"," +
                                            "\"thisChannelTitle\":\"original1\"," +
                                            "\"thisChannelDescrip\":\"Thischannelis...\"," +
                                            "\"triggerDescrip\":\"Tigger\"," +
                                            "\"arraylistThatChannelUrl\":[\"http://i.imgur.com/3hyC3Bz.png\"]," +
                                            "\"arraylistThatChannelTitle\":[\"channel1\"]," +
                                            "\"arraylistThatChannelDescrip\":[\"Thischannelis...\"]," +
                                            "\"arraylistActionDescrip\":[\"Play music from iHeart Radio\"]," +
                                            "\"isEnableNotificationFlag\":true," +
                                            "\"isEnableUseFlag\":true";

    public static String Str_FieldBabyMonitorListMockData = "{" +
                                                        "\"status\":\"0\", " +
                                                        "\"message\":\"ok\", " +
                                                        "\"commonItems\":[" +
                                                                        "{\"commonItemID\":\"000\", \"commonItemName\":\"Detect baby crying\"}, " +
                                                                        "{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying1\"}, " +
                                                                        "{\"commonItemID\":\"002\", \"commonItemName\":\"Detect baby crying2\"}, " +
                                                                        "{\"commonItemID\":\"003\", \"commonItemName\":\"Detect baby crying3\"}, " +
                                                                        "{\"commonItemID\":\"004\", \"commonItemName\":\"Detect baby crying4\"} " +
                                                                    "]" +
                                                    "}";

    public static String Str_FieldLullabyListMockData = "{" +
                                                    "\"status\":\"0\", " +
                                                    "\"message\":\"ok\", " +
                                                    "\"commonItems\":[" +
                                                                    "{\"commonItemID\":\"000\", \"commonItemName\":\"Lullaby List\"}, " +
                                                                    "{\"commonItemID\":\"001\", \"commonItemName\":\"Lullaby List1\"}, " +
                                                                    "{\"commonItemID\":\"002\", \"commonItemName\":\"Lullaby List2\"}, " +
                                                                    "{\"commonItemID\":\"003\", \"commonItemName\":\"Lullaby List3\"}, " +
                                                                    "{\"commonItemID\":\"004\", \"commonItemName\":\"Lullaby List4\"} " +
                                                                 "]" +
                                                 "}";


    public static String Str_RecipeListMockData = "{" +
                                                "\"status\":\"0\",\"message\":\"ok\", " +
                                                "\"recipes\":[" +
                                                            "{" +mockRecipeItem_GoodNightMode + "}," +
                                                            "{" +mockRecipeItem_GoodMorningMode+ "}," +
                                                            "{" +mockRecipeItem_NapTime+ "}," +
                                                            "{" +mockRecipeItem_Nursing+ "}," +
                                                            "{" +mockRecipeItem_Playtime+ "}" +

                                                          "]" +
                                                "}";

    public static String Str_HistoryListMockData = "{"+
            "\"status\":\"0\",\"message\":\"ok\",\"historyItems\":["+
            "{\"timeStamp\":\"1465021809578\",\"iconUrl\":\"test\",\"historyDescription\":\"Baby camara detected crying! Playing Classical Lulliby at 20% volume.\",\"recipeItem\":"+
            "{"+mockRecipeItem_GoodNightMode+"}"+ "},"+
            "{\"timeStamp\":\"1465021809578\",\"iconUrl\":\"test\",\"historyDescription\":\"Baby camara detected crying! Playing Classical Lulliby at 20% volume.\",\"recipeItem\":"+
            "{"+mockRecipeItem_GoodMorningMode+"}"+ "},"+
            "{\"timeStamp\":\"1465021809578\",\"iconUrl\":\"test\",\"historyDescription\":\"Baby camara detected crying! Playing Classical Lulliby at 20% volume.\",\"recipeItem\":"+
            "{"+mockRecipeItem_NapTime+"}"+ "},"+
            "{\"timeStamp\":\"1465021809578\",\"iconUrl\":\"test\",\"historyDescription\":\"Baby camara detected crying! Playing Classical Lulliby at 20% volume.\",\"recipeItem\":"+
            "{"+mockRecipeItem_Nursing+"}"+ "},"+
            "{\"timeStamp\":\"1465021809578\",\"iconUrl\":\"test\",\"historyDescription\":\"Baby camara detected crying! Playing Classical Lulliby at 20% volume.\",\"recipeItem\":"+
            "{"+mockRecipeItem_Playtime+"}"+ "}"+
            "]}";

    public static String Str_RecipeItemMockData = "{" +
                                                "\"status\":\"0\", " +
                                                "\"message\":\"ok\", " +mockRecipeItem_GoodNightMode+ "}";


    public static String Str_RecipeEditItemMockData =  "{" +
                                                        "\"status\":\"0\", " +
                                                        "\"message\":\"ok\", " +
                                                        "\"recipeItem\":{" +mockRecipeItem_GoodNightMode+"}," +
                                                        "\"triggerActions\":[" +
                                                                            "{" +
                                                                            "\"triggerActionID\":\"trig1\"," +
                                                                            "\"triggerActionType\":\"trigger\"," +
                                                                            "\"triggerActionName\":\"Baby Monitor\"," +
                                                                            "\"triggerActionDescrip\":\"Channel1 - trigger1\"," +
                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                            "{\"fieldName\":\"Baby Monitor Setting\", \"fieldValue\":{\"commonItemID\":\"001\", \"commonItemName\":\"Detect baby crying\"}, \"fieldType\":\"1\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                        "]" +
                                                                            "}," +
                                                                            "{" +
                                                                            "\"triggerActionID\":\"act1\"," +
                                                                            "\"triggerActionType\":\"action\"," +
                                                                            "\"triggerActionName\":\"Lullaby/White Noise\"," +
                                                                            "\"triggerActionDescrip\":\"Channel1 - Action1\"," +
                                                                            "\"arraylistTriggerActionField\":[" +
                                                                                                            "{\"fieldName\":\"Lullaby Setting\", \"fieldValue\":{\"commonItemID\":\"002\", \"commonItemName\":\"Play Classical Lullaby\"}, \"fieldType\":\"2\", \"isFieldEnable\":\"true\"" + "}, " +
                                                                                                            "{\"fieldName\":\"Playing Time Setting\", \"fieldValue\":{\"commonItemID\":\"003\", \"commonItemName\":\"30 minutes\"}, \"fieldType\":\"3\", \"isFieldEnable\":\"true\"" + "} " +
                                                                                                        "]" +
                                                                            "}" +
                                                                        "]" +
                                                "}";


    public static void testMockData() {
        try {
            Log.d(TAG, "origin json: " + Str_RecipeEditItemMockData);
            Bundle bundle = GSONUtil.toBundle(new JSONObject(Str_RecipeEditItemMockData));
            Log.w(TAG, "json to bundle: " + bundle.toString());

            JSONObject jsonObject = GSONUtil.toJSON(bundle);
            Log.d(TAG, "bundle to json: " + jsonObject.toString());
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

//
//    public static void initMockData()
//    {
//        MockServer.getInstance().clearResponseMap();
//
////        // ChannelList mock response ------------------------------------------------------
////        MockHttpResponse channelListResponse = new MockHttpResponse()
////                .setURL(AppHttpCommand.ReqChnnelList.getURL())
////                .setBody(Str_ChannelListMockData);
////
////        // fail mock response
////        ChannelItemList trackItem_ChannelItemList = new ChannelItemList();
////        trackItem_ChannelItemList.setStatus("-123");
////        trackItem_ChannelItemList.setMessage("Fail test");
////
////        MockHttpResponse channelListFailResponse = new MockHttpResponse()
////                .setURL(AppHttpCommand.ReqChnnelList.getFailedURL())
////                .setDataObject(trackItem_ChannelItemList);
////
//////        // Sets the mock responses to MockServer
////        MockServer.getInstance().addResponse(channelListResponse, channelListFailResponse);
//
//
//        // RecipeList mock response ------------------------------------------------------
//        MockHttpResponse recipeListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqRecipeList.getURL())
//                .setBody(Str_RecipeListMockData);
//
//        // fail mock response
//        RecipeItemList trackItem_RecipeItemList = new RecipeItemList();
//        trackItem_RecipeItemList.setStatus("-123");
//        trackItem_RecipeItemList.setMessage("Fail test");
//
//        MockHttpResponse recipeListFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqRecipeList.getFailedURL())
//                .setDataObject(trackItem_RecipeItemList);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(recipeListResponse, recipeListFailResponse);
//
////------------------------------------------------------------------------------------------------------
//
//
//        /**
//                * Device Message, The payload field may contain any arbitrary data structure.
//                */
////        String deviceMessage = "{\"type\": \"DEVICE_MESSAGE\", " +
////                "\"deviceId\": \"0fe09a0814465449f1c989478fcea4e6254060243b89b3c8f27b03269cbdd199\"," +
////                "\"payload\": {\"score\": \"12345\", \"time\":1234567890 }}";
//
//        String deviceInfo = "{\"type\": \"DEVICE_MESSAGE\", " +
//                "\"deviceId\": \"0fe09a0814465449f1c989478fcea4e6254060243b89b3c8f27b03269cbdd199\"," +
//                "\"payloadId\": " + PayloadType.GetRecipeList.getId() + "," +
//                "\"payload\": " + Str_RecipeListMockData + "}";
//
//        MockWebRtcResponse recipeListWebRTC_Response = new MockWebRtcResponse()
//                .setType(SilkMessageType.DEVICE_MESSAGE.toString())
//                .setPayloadType(PayloadType.GetRecipeList)
//                .setBody(deviceInfo);
//
//        MockServer.getInstance().addResponse(recipeListWebRTC_Response);
//
//
//
//
//
//
//
//
////        // CreateRecipe mock response ------------------------------------------------------
////        MockHttpResponse createRecipeResponse = new MockHttpResponse()
////                .setURL(AppHttpCommand.CreateRecipe.getURL())
////                .setBody(Str_RecipeItemMockData);
////
////        // fail mock response
////        RecipeItem trackItem_CreateRecipe = new RecipeItem();
////        trackItem_CreateRecipe.setStatus("-123");
////        trackItem_CreateRecipe.setMessage("Fail test");
////
////        MockHttpResponse createRecipeFailResponse = new MockHttpResponse()
////                .setURL(AppHttpCommand.CreateRecipe.getFailedURL())
////                .setDataObject(trackItem_CreateRecipe);
////
////        // Sets the mock responses to MockServer
////        MockServer.getInstance().addResponse(createRecipeResponse, createRecipeFailResponse);
//
//
//        // SwitchRecipeEnable mock response ------------------------------------------------------
//        MockHttpResponse recipeSwitchResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.SetSwitchUseEnable.getURL())
//                .setBody(Str_RecipeItemMockData);
//
//        // fail mock response
//        RecipeItem trackItem_RecipeSwitch = new RecipeItem();
//        trackItem_RecipeSwitch.setStatus("-123");
//        trackItem_RecipeSwitch.setMessage("Fail test");
//
//        MockHttpResponse recipeSwitchFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.SetSwitchUseEnable.getFailedURL())
//                .setDataObject(trackItem_RecipeSwitch);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(recipeSwitchResponse, recipeSwitchFailResponse);
//
//        // DeleteRecipe mock response ------------------------------------------------------
//
//        MockHttpResponse deleteRecipeItemResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.DeleteRecipeItem.getURL())
//                .setBody(Str_RecipeItemMockData);
//
//        // fail mock response
//        RecipeItem trackItem_DeleteRecipeItem = new RecipeItem();
//        trackItem_DeleteRecipeItem.setStatus("-123");
//        trackItem_DeleteRecipeItem.setMessage("Fail test");
//
//        MockHttpResponse deleteRecipeItemFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.DeleteRecipeItem.getFailedURL())
//                .setDataObject(trackItem_DeleteRecipeItem);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(deleteRecipeItemResponse, deleteRecipeItemFailResponse);
//
//
//        // CheckNow mock response ------------------------------------------------------
//
//        MockHttpResponse checkNowResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.CheckNow.getURL())
//                .setBody(Str_RecipeItemMockData);
//
//        // fail mock response
//        RecipeItem trackItem_CheckNow = new RecipeItem();
//        trackItem_CheckNow.setStatus("-123");
//        trackItem_CheckNow.setMessage("Fail test");
//
//        MockHttpResponse checkNowFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.CheckNow.getFailedURL())
//                .setDataObject(trackItem_CheckNow);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(checkNowResponse, checkNowFailResponse);
//
//        // TriggerActionItemList mock response ------------------------------------------------------
//
//        MockHttpResponse triggerActionItemListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqTriggerActionList.getURL())
//                .setBody(Str_TriggerActionItemListMockData);
//
//        // fail mock response
//        TriggerActionItem trackItem_TriggerActionItemList = new TriggerActionItem();
//        trackItem_TriggerActionItemList.setStatus("-123");
//        trackItem_TriggerActionItemList.setMessage("Fail test");
//
//        MockHttpResponse triggerActionItemListFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqTriggerActionList.getFailedURL())
//                .setDataObject(trackItem_TriggerActionItemList);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(triggerActionItemListResponse, triggerActionItemListFailResponse);
//
//
//
//        // FieldBabyMonitorList mock response ------------------------------------------------------
//
//        MockHttpResponse fieldBabyMonitorListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqFieldBabyMonitorList.getURL())
//                .setBody(Str_FieldBabyMonitorListMockData);
//
//        // fail mock response
//        CommonItem trackItem_FieldBabyMonitorList = new CommonItem();
//        trackItem_FieldBabyMonitorList.setStatus("-123");
//        trackItem_FieldBabyMonitorList.setMessage("Fail test");
//
//        MockHttpResponse fieldBabyMonitorListFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqFieldBabyMonitorList.getFailedURL())
//                .setDataObject(trackItem_FieldBabyMonitorList);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(fieldBabyMonitorListResponse, fieldBabyMonitorListFailResponse);
//
//
//        // FieldLullabyList mock response ------------------------------------------------------
//
//        MockHttpResponse fieldLullabyListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqFieldAudioList.getURL())
//                .setBody(Str_FieldLullabyListMockData);
//
//        // fail mock response
//        CommonItem trackItem_FieldLullabyList = new CommonItem();
//        trackItem_FieldLullabyList.setStatus("-123");
//        trackItem_FieldLullabyList.setMessage("Fail test");
//
//        MockHttpResponse fieldLullabyListFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqFieldAudioList.getFailedURL())
//                .setDataObject(trackItem_FieldLullabyList);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(fieldLullabyListResponse, fieldLullabyListFailResponse);
//
//
//        // EditRecipe mock response ------------------------------------------------------
//
//        MockHttpResponse editRecipeItemResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.EditRecipeItem.getURL())
//                .setBody(Str_RecipeEditItemMockData);
//
//        // fail mock response
//        RecipeEditItem trackItem_EditRecipeItem = new RecipeEditItem();
//        trackItem_EditRecipeItem.setStatus("-123");
//        trackItem_EditRecipeItem.setMessage("Fail test");
//
//        MockHttpResponse editRecipeItemFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.EditRecipeItem.getFailedURL())
//                .setDataObject(trackItem_EditRecipeItem);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(editRecipeItemResponse, editRecipeItemFailResponse);
//
//
//        // HistoryItemList mock response ------------------------------------------------------
//        MockHttpResponse historyListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqHistoryItemList.getURL())
//                .setBody(Str_HistoryListMockData);
//
//        // fail mock response
//        HistoryItem historyListFail = new HistoryItem();
//        historyListFail.setStatus("-123");
//        historyListFail.setMessage("Fail test");
//
//        MockHttpResponse historyListFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqHistoryItemList.getFailedURL())
//                .setDataObject(historyListFail);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(historyListResponse, historyListFailResponse);
//
//        // ReqBrowseList mock response ------------------------------------------------------
//
//        MockHttpResponse browseListResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqBrowseList.getURL())
//                .setBody(Str_RecipeListMockData);
//
//        // fail mock response
//        RecipeItem browseListFail = new RecipeItem();
//        browseListFail.setStatus("-123");
//        browseListFail.setMessage("Fail test");
//
//        MockHttpResponse browseFailResponse = new MockHttpResponse()
//                .setURL(AppHttpCommand.ReqBrowseList.getFailedURL())
//                .setDataObject(browseListFail);
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(browseListResponse, browseFailResponse);
//    }
//
//
//
//
//
//
//    public static boolean updateRecipeList(RecipeItem recipeItem) {
//        String newRecipeStr = null;
//        try {
//            newRecipeStr = recipeItem.toJSONObject(new Gson()).toString();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if(newRecipeStr != null){
//            int cutPoint = MockData.Str_RecipeListMockData.indexOf("\"recipes\":[")+11;
//            MockData.Str_RecipeListMockData = MockData.Str_RecipeListMockData.substring(0, cutPoint) + newRecipeStr +","+ MockData.Str_RecipeListMockData.substring(cutPoint);
//            MockData.initMockData();
//        }
//        return true;
//    }
//
//    public static boolean updateEditRecipeList(String recipeType)
//    {
//        if(recipeType.equalsIgnoreCase("Good Night Mode"))
//        {
//            Str_TriggerActionItemListMockData = Str_TriggerActionItemListMockData_GoodNightMode;
//            MockData.initMockData();
//        }
//        else  if(recipeType.equalsIgnoreCase("Good Morning Mode"))
//        {
//            Str_TriggerActionItemListMockData = Str_TriggerActionItemListMockData_GoodMorningMode;
//            MockData.initMockData();
//        }
//        else  if(recipeType.equalsIgnoreCase("Nap Time"))
//        {
//            Str_TriggerActionItemListMockData =  Str_TriggerActionItemListMockData_NapTime;
//            MockData.initMockData();
//        }
//        else  if(recipeType.equalsIgnoreCase("Nursing"))
//        {
//            Str_TriggerActionItemListMockData =  Str_TriggerActionItemListMockData_Nursing;
//            MockData.initMockData();
//        }
//        else  if(recipeType.equalsIgnoreCase("Playtime"))
//        {
//            Str_TriggerActionItemListMockData =  Str_TriggerActionItemListMockData_Playtime;
//            MockData.initMockData();
//        }
//        return true;
//    }


//
}
