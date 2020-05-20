#include "JobNode.h"

JobNode::JobNode(std::shared_ptr<Job> job)
{
	this->_job = job;
}

std::shared_ptr<JobNode> JobNode::insertEdge(std::shared_ptr<JobNode> dest, bool isDeterministic)
{
	EdgeInfo info;
	info.isDeterministic = isDeterministic;
	this->_edges.push_back(std::make_pair(dest, info));
	return dest;
}

std::vector<std::shared_ptr<JobNode>> JobNode::getDeterministicEdges()
{
	std::vector<std::shared_ptr<JobNode>> deterministicEdges;
	for (auto edge : this->_edges)
		if (edge.second.isDeterministic)
			deterministicEdges.push_back(edge.first);
	return deterministicEdges;
}


std::vector<std::shared_ptr<JobNode>> JobNode::getNonDeterministicEdges()
{
	std::vector<std::shared_ptr<JobNode>> nonDeterministicEdges;
	for (auto edge : this->_edges)
		if (!edge.second.isDeterministic)
			nonDeterministicEdges.push_back(edge.first);
	return nonDeterministicEdges;
}

std::vector<std::pair<std::shared_ptr<JobNode>, EdgeInfo>> JobNode::getAllEdges()
{
	return this->_edges;
}