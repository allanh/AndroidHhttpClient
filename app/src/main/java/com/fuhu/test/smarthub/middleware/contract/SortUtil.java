package com.fuhu.test.smarthub.middleware.contract;

public enum SortUtil {
    CategoryTypeId("CategoryType"),
    LastUpdateDate("LastUpdateTime");

    private String identifier;

    private SortUtil(final String identifier){
        this.identifier=identifier;
    }
//
//    public static List<IMailItem> getSortedList(List<IMailItem> list, final SortUtil sortType, Class<?> sortClass) {
//        Collections.sort(list, new Comparator<IMailItem>() {
//            @Override
//            public int compare(IMailItem lhs, IMailItem rhs) {
//                ContentItem lhsItem = (ContentItem) lhs;
//                ContentItem rhsItem = (ContentItem) rhs;
//
//                switch(sortType) {
//                    case CategoryTypeId:
//                        return lhsItem.getCategoryType().getID().compareTo(rhsItem.getCategoryType().getID());
//
//                    case LastUpdateDate:
//                        long lhsTime = lhsItem.getLastUpdateTime();
//                        long rhsTime = rhsItem.getLastUpdateTime();
//                        if(lhsTime > rhsTime)
//                            return -1;
//                        else if (lhsTime == rhsTime)
//                            return 0;
//                        else
//                            return 1;
//
//                    default:
//                        return lhsItem.getCategoryType().getID().compareTo(rhsItem.getCategoryType().getID());
//
//                }
//            }
//        });
//        return list;
//    }
//
//    /**
//     * Sort by distance
//     * @param list place list
//     * @param mLastLocation the device's location
//     * @return
//     */
//    public static List<ContentItem> getSortedPlaceList(List<ContentItem> list, final Location mLastLocation) {
//        if (list != null && mLastLocation != null) {
//            Collections.sort(list, new Comparator<ContentItem>() {
//                @Override
//                public int compare(ContentItem lhs, ContentItem rhs) {
//                    Location lhsLocation = lhs.getGeometry().getLocation().get();
//                    Location rhsLocation = rhs.getGeometry().getLocation().get();
//
//                    if (lhsLocation != null && rhsLocation != null) {
//                        // compute the distance in meters between place and device
//                        float lhsDistance = lhsLocation.distanceTo(mLastLocation);
//                        float rhsDistance = rhsLocation.distanceTo(mLastLocation);
//
//                        // from near to far
//                        if (lhsDistance > rhsDistance)
//                            return 1;
//                        else if (lhsDistance == rhsDistance)
//                            return 0;
//                        else
//                            return -1;
//
//                    }
//
//                    return 0;
//                }
//            });
//        }
//        return list;
//    }
}
