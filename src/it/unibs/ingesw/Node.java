package it.unibs.ingesw;

import java.util.ArrayList;

public class Node implements NameGiver{
	
	private int netId;
	private int nodeId;
	private String nodeName;
	private ArrayList<Link> links;
	
	public Node(int netId, int nodeId, String nodeName) {
		
		this.netId = netId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		links = new ArrayList<Link>();
	}
	
	public void addLink (Link link) {
		links.add(link);
	}

	
	
	
	
	
	public int getNetId() {
		return netId;
	}

	public void setNetId(int netId) {
		this.netId = netId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

	@Override
	public String getName() {
		return nodeName;
	}
	
	
	
}
