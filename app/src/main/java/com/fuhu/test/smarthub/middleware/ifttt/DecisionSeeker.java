package com.fuhu.test.smarthub.middleware.ifttt;

import com.fuhu.test.smarthub.middleware.componet.Log;

import java.util.ArrayList;
import java.util.List;


public abstract class DecisionSeeker {
	private static final String TAG = DecisionSeeker.class.getSimpleName();
	private List<PondoIFTTT> findCandidates;

    public void onSTT(final String searchWord, final PondoIFTTT mRoot) {
        List<String> wordList = new ArrayList<String>();
        wordList.add(searchWord);
        onSTT(wordList, mRoot);
    }

    public void onSTT(final List<String> searchWords, final PondoIFTTT mRoot){
		Initialization.samplePondoIFTTTRoot=mRoot;
		List<PondoIFTTT> conn=new ArrayList<PondoIFTTT>();
		List<Integer> searchGrade=new ArrayList<Integer>();
		int maxIndex=0;
		IFTTTPattern searchIndex=null;

		for(int i=0;i<searchWords.size();i++){
			Log.d(TAG, "searchWord: " + searchWords.get(i));
			searchGrade.add(0);
			IFTTTPattern tmp=found(searchWords.get(i),(IFTTTPattern)Initialization.samplePondoIFTTTRoot);
			if(tmp!=null){
				conn.add((PondoIFTTT) tmp);
				searchGrade.set(tmp.getIdentifier(), searchGrade.get(tmp.getIdentifier())+1);

                Log.d(TAG, "id: " + searchGrade.get(tmp.getIdentifier()) + " max: " + searchGrade.get(maxIndex));
				if(searchGrade.get(tmp.getIdentifier())>searchGrade.get(maxIndex)){
					maxIndex=tmp.getIdentifier();
					searchIndex=tmp;
				}
            }
		}

		if (searchIndex != null) {
			Log.d(TAG, "searchIndex: " + searchIndex.getMainRecoWords());
		}

		if(searchIndex!=null && searchGrade.get(maxIndex)==searchIndex.getTotalLevel()){
			onComplete(findCandidates);
		}else{
			if(searchIndex!=null){
				for(PondoIFTTT tmp:findCandidates){
					if(!((PondoIFTTT) tmp.getPraentNode()).isFound()){
						onTTS(findCandidates,(String) tmp.onThenThat());
						break;
					}
				}
			}else{
				//Fail case
				onTTS(null,"Try Again");
			}
		}
		
	}
	
	private IFTTTPattern found(final String keywords, final IFTTTPattern myroot){
		IFTTTPattern rtnType=null;
        Log.d(TAG, "isLeave: " + myroot.isLeave());
		if(myroot.isLeave()){
            Log.d(TAG, "keyword: " + keywords + " RecoWords: " + myroot.getMainRecoWords());
			if(myroot.getMainRecoWords().equals(keywords)){
				myroot.setFound(true);
				rtnType=myroot;
			}else{
				myroot.setFound(false);
			}
		}else{
			boolean compare=false;
			for(IFTTTPattern tmp:myroot.getSonNodes()){
				if(tmp instanceof PondoIFTTT){
					if(tmp.equals(keywords)){
						rtnType=(PondoIFTTT)tmp;
						rtnType.setFound(true);
						compare=true;
						//break;
					}else{
						((PondoIFTTT)tmp).setFound(false);
					}
				}
                Log.d(TAG, "compare: " + compare);
				if(!compare){
					for(IFTTTPattern searchItem:myroot.getSonNodes()){
						return found(keywords,searchItem);
					}
				}
			}
			
		}
		return rtnType;
	}
	
	public abstract void onTTS(final List<PondoIFTTT> myroot,final String sentence);
	public abstract void onComplete(final List<PondoIFTTT> myroot);	
}
