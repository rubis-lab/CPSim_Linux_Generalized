#pragma once
#include <vector>
#include <memory>
#include "Job.h"

typedef struct EdgeInfo
{
	bool isDeterministic = true;
	// ... Other stuff?
} EdgeInfo;

class JobNode
{
	std::vector<std::pair<std::shared_ptr<JobNode>, EdgeInfo>> _edges;
	std::shared_ptr<Job> _job = nullptr; // Actual job we represent.
public:
	std::shared_ptr<JobNode> insertEdge(std::shared_ptr<JobNode> dest, bool isDeterministic);
	JobNode(std::shared_ptr<Job> job);
	std::vector<std::shared_ptr<JobNode>> getDeterministicEdges();
	std::vector<std::shared_ptr<JobNode>> getNonDeterministicEdges();
	std::vector<std::pair<std::shared_ptr<JobNode>, EdgeInfo>> getAllEdges();
};