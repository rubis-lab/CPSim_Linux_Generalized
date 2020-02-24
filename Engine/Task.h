/** 이 파일은 Task 모듈의 의사코드 작성 파일입니다.
 *  독시파일을 위해 만들어졌으며, 형태는 아래와 같이 맞추자
 *  @file Task.cpp
 *  @brief Pseudo Codes for Engine-Task
 *  @page Task
 *  @author Seonghyeon Park
 *  @date 2020-02-18
 *  @section Logic
 *  Task 로직은 다음과 같다.
 *  1. Specification 모듈 실행 및 성공값 리턴 받고 아닐 경우, 에러 출력
 *  2. Specification에 필요한 인스턴스 변수들 생성 및 CAN 인터페이스 모듈 생성
 *  3.
 *
 *
 */
#include <cstdio>

/// @class Task
class Task
{
public:
	virtual void Update() = 0;
	virtual bool ShouldWeExecute() = 0;

	virtual ~Task()
	{
		printf("Destroyed\n");
	}
};