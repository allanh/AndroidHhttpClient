package com.fuhu.test.smarthub.middleware.ifttt;

public class Initialization {
	public static PondoIFTTT samplePondoIFTTTRoot=new PondoIFTTT("Pondo",0,"What action are you or your baby doing? Feeding, pumping, sleeping, or changing diaper ", "my Pondo","Hi Pondo", "Hello Pondo");;
	static{
		//Create Decision Tree
		PondoIFTTT samplePondoIFTTTNode=new PondoIFTTT("Feed",0,"Left or right breast", "feeding","Hi Pondo");
		samplePondoIFTTTRoot.addSonNodes(samplePondoIFTTTNode);
		samplePondoIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.setLeave();

		
		samplePondoIFTTTNode=new PondoIFTTT("Sleep",1,"How lon is he sleeping?","Sleeping");
		samplePondoIFTTTRoot.addSonNodes(samplePondoIFTTTNode);
		samplePondoIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.setLeave();

		
		samplePondoIFTTTNode=new PondoIFTTT("Diaper",2,"Is diaper wet or dirty?","Diaper");
		PondoIFTTT sampleSonPondoIFTTTNode=new PondoIFTTT("Diaper",2,"Record!","Wet");
		sampleSonPondoIFTTTNode.setLeave();
		samplePondoIFTTTNode.addSonNodes(sampleSonPondoIFTTTNode);
		sampleSonPondoIFTTTNode.setPraentNode(samplePondoIFTTTNode);
		sampleSonPondoIFTTTNode=new PondoIFTTT("Diaper",2,"Record!","Dirty");
		sampleSonPondoIFTTTNode.setLeave();
		samplePondoIFTTTNode.addSonNodes(sampleSonPondoIFTTTNode);
		sampleSonPondoIFTTTNode.setPraentNode(samplePondoIFTTTNode);
		samplePondoIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		
		samplePondoIFTTTRoot.addSonNodes(samplePondoIFTTTNode);
		samplePondoIFTTTNode=new PondoIFTTT("Pump",3,"How many?","Pumping");
		samplePondoIFTTTNode.setPraentNode(samplePondoIFTTTRoot);
		samplePondoIFTTTRoot.setLeave();

	}
}
