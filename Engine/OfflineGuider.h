/** 이 파일은 OfflineGuider 모듈의 의사코드 작성 파일입니다.
 *
 *  @file OfflineGuider.cpp
 *  @brief Pseudo Codes for Engine-OfflineGuider
 *  @page OfflineGuider
 *  @author Seonghyeon Park
 *  @date 2020-02-18
 *  @section Logic
 *  OfflineGuider 로직은 다음과 같다.
 *  1. 생성된 스케줄 시뮬레이션 정보를 토대로 각 Job의 선행관계를 모두 나타낸다.
 *  2. 하나의 Job마다 선행관계에 대해 세 종류의 관계를 모두 나타낸다.
 *  3. 중복되는 Job 선행관계인 경우 합집합에 넣어 분류한다.
 *  4. 그렇게 Job 자신을 기준으로 선행자와 후행자를 분류해놓는다.
 *  5. 그래프 작성에 사용될 자료구조는 좀 더 생각해보고 결정하자.
 *
 *
 */

 /// @class OfflineGuider
class OfflineGuider {

};