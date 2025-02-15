package store.seub2hu2.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import store.seub2hu2.community.dto.FunctionCheckDto;
import store.seub2hu2.community.vo.Crew;
import store.seub2hu2.community.vo.CrewMember;
import store.seub2hu2.community.vo.Notice;
import store.seub2hu2.util.RequestParamsDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrewMapper {

    void insertCrew(@Param("crew") Crew crew);
    void insertCrewMember(@Param("member") CrewMember member);
    List<Crew> getCrews(@Param("dto") RequestParamsDto dto);
    int getTotalRowsForCrew(@Param("dto")RequestParamsDto dto);
    Crew getCrewDetailByNo(@Param("no") int crewNo);

    List<Crew> getCrewsTopFive(@Param("condition") Map<String, Object> condition);
    List<Integer> getCrewMembers(@Param("no") int crewNo);
    List<CrewMember> getByCrewNo(@Param("no") int crewNo);
    List<Crew> getCrewByUserNo(@Param("userNo") int userNo);

    void updateCrew(@Param("crew") Crew crew); // 크루 게시글 수정 및 삭제
    void updateCrewCnt(@Param("crew") Crew crew);
    void updateCrewCondition(@Param("no") int crewNo, @Param("condition") String condition); // 크루 가입 가능 여부 수정
    void updateCrewMember(@Param("member") CrewMember member);
    int getCrewMemberCnt(@Param("no") int crewNo);
    void updateReader(@Param("userNo") int userNo, @Param("crewNo") int crewNo );
    void exitCrew(@Param("readerNo") int readerNo, @Param("crewNo") int crewNo);

    void updateCnt(@Param("crew") Crew crew);

    int getCrewNoByReportNo(@Param("no") int reportNo);
    void updateCrewReport(@Param("no") int crewNo);
}
