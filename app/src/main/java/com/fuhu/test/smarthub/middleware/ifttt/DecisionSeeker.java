package com.fuhu.test.smarthub.middleware.ifttt;

import com.fuhu.test.smarthub.middleware.componet.Log;

import java.util.ArrayList;
import java.util.List;


public abstract class DecisionSeeker {
	private static final String TAG = DecisionSeeker.class.getSimpleName();
	private List<PondoIFTTT> findCandidates;
    private static Boolean isfind=false;


//    public void onSTT(final String searchWord, final PondoIFTTT mRoot) {
//        List<String> wordList = new ArrayList<String>();
//        wordList.add(searchWord);
//        onSTT(wordList, mRoot);
//    }

    public void onSTT(final List<String> searchWords, final PondoIFTTT mRoot){
		Initialization.samplePondoIFTTTRoot=mRoot;
		List<PondoIFTTT> conn=new ArrayList<PondoIFTTT>();
        List<PondoIFTTT> testList =new ArrayList<PondoIFTTT>();
        List<Integer> searchGrade=new ArrayList<Integer>();
        isfind=false;
		int maxIndex=0;
		IFTTTPattern searchIndex=null;

		for(int i=0;i<searchWords.size();i++){
			Log.d(TAG, "searchWord: " + searchWords.get(i));
			searchGrade.add(0);
            List<IFTTTPattern> foundNodes=new ArrayList<IFTTTPattern>();
			IFTTTPattern tmp=found(searchWords.get(i),(IFTTTPattern)Initialization.samplePondoIFTTTRoot,foundNodes);


			if(tmp!=null){
                if(searchIndex==null){
                    searchIndex=tmp;
                }

                // Allan test
                testList.add((PondoIFTTT)tmp);

                conn.add((PondoIFTTT) tmp);
                if(tmp.getIdentifier()>=searchGrade.size()){
                    for(int j=searchGrade.size()-1;j<=tmp.getIdentifier();j++){
                        searchGrade.add(0);
                    }
                }
				searchGrade.set(tmp.getIdentifier(), searchGrade.get(tmp.getIdentifier())+1);

                Log.d(TAG, "id: " + searchGrade.get(tmp.getIdentifier()) + " max: " + searchGrade.get(maxIndex));
				if(searchGrade.get(tmp.getIdentifier())>searchGrade.get(maxIndex)){
					maxIndex=tmp.getIdentifier();
					searchIndex=tmp;
				}
            } else {
                Log.w(TAG, "not found.");
            }
		}

        // Allan test
//        if (testList.size() > 0) {
//            onComplete(testList);
//        }

        if (searchIndex != null) {
			Log.d(TAG, "searchIndex: " + searchIndex.getMainRecoWords());
		}

		if(searchIndex!=null && searchGrade.get(maxIndex)==searchIndex.getTotalLevel()){
			onComplete(findCandidates);
		}else{
			if(searchIndex!=null){
                Log.d(TAG, "searchIndex isn't null");
                List<PondoIFTTT> findCandidates=new ArrayList<PondoIFTTT>();
                findCandidates.add((PondoIFTTT)searchIndex);
                onTTS(findCandidates, (String) ((PondoIFTTT) searchIndex).onThenThat());

//                for(PondoIFTTT tmp:findCandidates){
//					if(!((PondoIFTTT) tmp.getPraentNode()).isFound()){
//						onTTS(findCandidates,(String) tmp.onThenThat());
//						break;
//					}
//				}
			}else{
                Log.w(TAG, "searchIndex is null");
                //Fail case
				//onTTS(null,"Try Again");
			}
		}
		
	}
	private IFTTTPattern found(final String keywords, final IFTTTPattern myroot,  final List<IFTTTPattern> foundNodes){
		IFTTTPattern rtnType=null;
        Log.d(TAG, "isLeave: " + myroot.isLeave());
        boolean compare=false;
        List<IFTTTPattern> sonNodes;
        sonNodes=new ArrayList<IFTTTPattern>();
        sonNodes.add(myroot);

        if (sonNodes != null) {
            for (IFTTTPattern tmp : sonNodes) {
                Log.d(TAG, "tmp: " + tmp.getMainRecoWords() + " keywords: " + keywords);

                if (tmp instanceof PondoIFTTT) {
                    //Log.d(TAG, "tmp: " + tmp.getMainRecoWords() + " keywords: " + keywords);
                    if (((PondoIFTTT) tmp).similarMatch(keywords)) {
                        rtnType = (PondoIFTTT) tmp;
                        rtnType.setFound(true);
                        compare = true;
                        //break;
                    }
                    //else {
                      //  ((PondoIFTTT) tmp).setFound(false);
                    //}
                }
                Log.d(TAG, "compare: " + compare);
                if (!compare) {
                    if(myroot.getSonNodes()!=null) {

                        for (IFTTTPattern searchItem : myroot.getSonNodes()) {
                            found(keywords, searchItem, foundNodes);
                        }
                    }
                }else{
                    foundNodes.add(rtnType);
                    if(myroot.getSonNodes()!=null) {
                        for (IFTTTPattern searchItem : myroot.getSonNodes()) {
                            found(keywords, searchItem, foundNodes);
                        }
                    }
                }

            }
        }

        if(foundNodes!=null && foundNodes.size()>0 && foundNodes.get(0)!=null) {
            Log.i(TAG, "isFind: " + isfind + " found(0): " + foundNodes.get(0).getMainRecoWords());
            //synchronized (isfind) {
              //  if (!isfind) {
                    isfind = true;
                    Log.d(TAG, "return node");
                    return foundNodes.get(0);
                //}
            //}
        }
        return null;





//		if(myroot.isLeave()){
//            Log.d(TAG, "keyword: " + keywords + " RecoWords: " + myroot.getMainRecoWords());
//			if(((PondoIFTTT)myroot).similarMatch(keywords)){
//				myroot.setFound(true);
//				rtnType=myroot;
//			}else{
//				myroot.setFound(false);
//			}
//		}else{
//			boolean compare=false;
//            List<IFTTTPattern> sonNodes;
//            //if(myroot.getPraentNode()==null){
//                sonNodes=new ArrayList<IFTTTPattern>();
//                sonNodes.add(myroot);
//            //}else{
//              //  sonNodes=myroot.getSonNodes();
//            //}
//            if (sonNodes != null) {
//                for (IFTTTPattern tmp : sonNodes) {
//                    Log.d(TAG, "tmp: " + tmp.getMainRecoWords() + " keywords: " + keywords);
//
//                    if (tmp instanceof PondoIFTTT) {
//                        //Log.d(TAG, "tmp: " + tmp.getMainRecoWords() + " keywords: " + keywords);
//                        if (((PondoIFTTT) tmp).similarMatch(keywords)) {
//                            rtnType = (PondoIFTTT) tmp;
//                            rtnType.setFound(true);
//                            compare = true;
//                            //break;
//                        } else {
//                            ((PondoIFTTT) tmp).setFound(false);
//                        }
//                    }
//                    Log.d(TAG, "compare: " + compare);
//                    if (!compare) {
//                        for (IFTTTPattern searchItem : myroot.getSonNodes()) {
//                            return found(keywords, searchItem);
//                        }
//                    }
//                }
//            }
//
//		}
//		return rtnType;
	}
	
	public abstract void onTTS(final List<PondoIFTTT> myroot,final String sentence);
	public abstract void onComplete(final List<PondoIFTTT> myroot);	
}
