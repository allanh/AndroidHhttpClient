package com.fuhu.test.smarthub.middleware.ifttt;

import java.util.List;


public interface IFTTTPattern {
	public Object onThenThat();
	public boolean onIfThis(final IFTTTPattern pattern);
	public List<IFTTTPattern> getSonNodes();
	public boolean isLeave();
	public String getMainRecoWords();
	public void setFound(final boolean isFound);
	public int getIdentifier();
	public int getTotalLevel();
	public IFTTTPattern getPraentNode();
}
