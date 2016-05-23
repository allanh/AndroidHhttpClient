package com.fuhu.test.smarthub.ifttt;

public class Initialization {
	public static PondoIFTTT samplePondoIFTTTRoot=new PondoIFTTT("Hello",0,"What action are you or your baby doing? Feeding, pumping, sleeping, or changing diaper ", "Nikki","Pondo", "honda");;
	static{
		//Create Decision Tree
		PondoIFTTT samplePondoIFTTTNode=new PondoIFTTT("Feed",0,"Left or right breast", "feeding","Hi Pondo");
		samplePondoIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.addSonNodes(samplePondoIFTTTNode);
//		samplePondoIFTTTNode.setLeave();

		PondoIFTTT kIDIFTTTNode = new PondoIFTTT("Left",0,"Record", "feeding","Hi Pondo");
		kIDIFTTTNode.setPraentNode(samplePondoIFTTTNode);
		samplePondoIFTTTNode.addSonNodes(kIDIFTTTNode);
		//kIDIFTTTNode.setLeave();

		PondoIFTTT sleepIFTTTNode=new PondoIFTTT("Sleep",1,"How lon is he ?","Sleeping");
		sleepIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.addSonNodes(sleepIFTTTNode);
		//samplePondoIFTTTNode.setLeave();


		PondoIFTTT diaperIFTTTNode=new PondoIFTTT("Diaper",2,"Is diaper wet or dirty?","Diaper");

		PondoIFTTT wetIFTTTNode=new PondoIFTTT("Wet",2,"Record!","Wet");
		//sampleSonPondoIFTTTNode.setLeave();
		wetIFTTTNode.setPraentNode(diaperIFTTTNode);
		diaperIFTTTNode.addSonNodes(wetIFTTTNode);

		PondoIFTTT dirtyIFTTTNode=new PondoIFTTT("Dirty",2,"Record!","Dirty");
		//sampleSonPondoIFTTTNode.setLeave();
		dirtyIFTTTNode.setPraentNode(diaperIFTTTNode);
		diaperIFTTTNode.addSonNodes(dirtyIFTTTNode);

		diaperIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.addSonNodes(diaperIFTTTNode);

		PondoIFTTT pumpIFTTTNode=new PondoIFTTT("Pump",3,"How many?","Pumping");
		pumpIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.addSonNodes(pumpIFTTTNode);
		//samplePondoIFTTTNode.setLeave();

	}
}
