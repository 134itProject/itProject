package com.marre.cm.mapper;

import com.marre.cm.domain.SysHall;
import com.marre.cm.domain.SysSession;
import com.marre.cm.domain.vo.SysSessionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 电影场次持久层
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysSessionMapper {

    List<SysSession> findByVo(SysSessionVo sysSessionVo);

    List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession);

    SysSession findSessionById(Long id);

    SysSession findOneSession(Long id);

    int addSession(SysSession sysSession);

    int updateSession(SysSession sysSession);

    int deleteSession(Long id);

    List<SysSession> findSessionByMovieId(Long movieId);
}
