#include "CodeWrapper.h"
#include <fstream>
#include <sstream>

std::string CodeWrapper::wrap(std::string mainContent, std::string otherContent, std::string sharedHeaderPath)
{
	std::string wrapped;
	std::string sharedHeader;
	std::ifstream fileStream(sharedHeaderPath);
	std::stringstream buffer;

	buffer << fileStream.rdbuf();

	wrapped = sharedHeader + "\r\n\r\n";
	wrapped += "extern \"C\" void sim_main();";
	wrapped += "\r\n\r\n";
	wrapped += "void sim_main()\r\n{\r\n";
	wrapped += mainContent;
	wrapped += "\r\n}\r\n\r\n";
	wrapped += otherContent + "\r\n";

	return wrapped;
}