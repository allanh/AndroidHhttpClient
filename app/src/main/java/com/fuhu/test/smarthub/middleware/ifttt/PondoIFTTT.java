package com.fuhu.test.smarthub.middleware.ifttt;

import java.util.ArrayList;
import java.util.List;


public class PondoIFTTT implements IFTTTPattern{
	private List<IFTTTPattern> 	sonNodes		=null;
	private int 				totalLevel		=0;
	private List<String> 		similarWrods	=null;
	private String 				mainRecoWords	=null;
	private int					identifier		=0;
	private boolean				isLeave			=false;
	private IFTTTPattern		praentNode		=null;
	private String				recmmendation	=null;
	private boolean				isFound			=false;
	
	public PondoIFTTT(final String mainRecoWords, final int identifier, final String recmmendation, final String... similarWords){
		this.mainRecoWords=mainRecoWords;
		this.identifier=identifier;
		this.recmmendation=recmmendation;
		this.similarWrods=new ArrayList<String>();
		for(String similar:similarWords){
			similarWrods.add(similar);
		}
	}
	
	@Override
	public Object onThenThat() {
		// TODO Auto-generated method stub
		return recmmendation;
	}

	@Override
	public boolean onIfThis(final IFTTTPattern pattern) {
		// TODO Auto-generated method stub
		return this.equals(pattern);
	}
	
	@Override
	public boolean equals(final Object obj){
		if(obj!=null && obj instanceof PondoIFTTT){
			PondoIFTTT comObj=(PondoIFTTT)obj;
			if(comObj.identifier==this.identifier && comObj.mainRecoWords.equals(this.mainRecoWords)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	public boolean equals(final String keywrods){
			if(this.mainRecoWords.equalsIgnoreCase(keywrods)){
				return true;
			}else{
				for(String tmp:similarWrods){
					if(tmp.equalsIgnoreCase(keywrods)){
						return true;
					}
				}
			}
		return false;
	}
	@Override
	public int hashCode(){
		return identifier+mainRecoWords.hashCode();
	}

	/**
	 * 
	 * @return
	 */
	public IFTTTPattern getPraentNode() {
		return praentNode;
	}

	/**
	 * 
	 * @param praentNode
	 */
	public void setPraentNode(final IFTTTPattern praentNode) {
		this.praentNode = praentNode;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isLeave() {
		return isLeave;
	}

	/**
	 * 
	 */
	public void setLeave() {
		this.isLeave = true;
	}

	/**
	 * 
	 * @return
	 */
	public List<IFTTTPattern> getSonNodes() {
		return sonNodes;
	}

	/**
	 * 
	 * @param sonNodes
	 */
	public void setSonNodes(final List<IFTTTPattern> sonNodes) {
		this.sonNodes = sonNodes;
	}
	
	/**
	 * 
	 * @param sonNode
	 */
	public void addSonNodes(final IFTTTPattern sonNode) {
		if(this.sonNodes==null){
			this.sonNodes=new ArrayList<IFTTTPattern>();
		}
		this.sonNodes.add(sonNode);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * 
	 * @param identifier
	 */
	public void setIdentifier(final int identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMainRecoWords() {
		return mainRecoWords;
	}
	
	/**
	 * 
	 * @param mainRecoWords
	 */
	public void setMainRecoWords(final String mainRecoWords) {
		this.mainRecoWords = mainRecoWords;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getSimilarWrods() {
		return similarWrods;
	}
	
	/**
	 * 
	 * @param similarWrods
	 */
	public void setSimilarWrods(final List<String> similarWrods) {
		this.similarWrods = similarWrods;
	}
	
	/**
	 * 
	 * @param similarWrod
	 */
	public void addSimilarWrods(final String similarWrod) {
		if(this.similarWrods==null){
			this.similarWrods=new ArrayList<String>();
		}
		this.similarWrods.add(similarWrod);
	}

	/**
	 * 
	 * @return
	 */
	public String getRecmmendation() {
		return recmmendation;
	}

	/**
	 * 
	 * @param recmmendation
	 */
	public void setRecmmendation(final String recmmendation) {
		this.recmmendation = recmmendation;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public int getTotalLevel() {
		return totalLevel;
	}

	public void setTotalLevel(int totalLevel) {
		this.totalLevel = totalLevel;
	}
}
