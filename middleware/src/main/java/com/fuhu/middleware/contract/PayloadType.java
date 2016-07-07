package com.fuhu.middleware.contract;

import java.util.HashMap;
import java.util.Map;

public enum PayloadType {
    /**
     * declare INSTANCE of this Enum
     */
    INSTANCE,

    /**
     * Night Light
     */
    GetNightLightSync("1001"),
    PostUpdateColor("1002"),
    PostUpdateSchedule("1003"),
    PostUpdatePreviouslyColors("1004"),

    /**
     * Lullaby
     */
    GetLullabySync("2001"),
    PostUpdatePlayer("2002"),
    PostUpdateSchedules("2003"),
    PushUpdateVolume("2004"),
    PushUpdatePlayer("2005"),

    /**
     * Common
     */
    PushCheckDeviceInfo("3001"),

    /**
     * Hub Information
     */
    GetHubInfo("4001"),

    /**
     * IFTTT
     */
    GetRecipeList("5001"),
    GetBrowseList("5002"),
    GetChannelList("5003"),
    GetTriggerOrActionList("5004"),
    GetHistoryItemList("5005"),
    GetKidList("5006"),
    GetLullabyList("5007"),
    GetBabyMonitorList("5008"),
    PostCreateRecipe("5009"),
    PostDeleteRecipe("5010"),
    PostEditRecipe("5011"),
    PostCheckNow("5012"),
    PostSwitchRecipeUseEnable("5013"),
    ;

    private String typeId = null;

    private PayloadType() {}

    private PayloadType(final String typeId){
        this.typeId = typeId;
    }

    private static Map<String, PayloadType> lookupTable = new HashMap<String, PayloadType>();

    static{
        for(PayloadType tmp: PayloadType.values()){
            lookupTable.put(tmp.getId(), tmp);
        }
    }

    public String getId() {
        return typeId;
    }

    public void setId(final String typeId){
        this.typeId = typeId;
    }

    /**
     * Finding device message type
     * @param typeId
     * @return
     */
    public static PayloadType lookup(final String typeId){
        return lookupTable.get(typeId);
    }
}
