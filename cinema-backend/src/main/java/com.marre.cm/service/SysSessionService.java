package com.marre.cm.service;

import com.marre.cm.domain.SysSession;
import com.marre.cm.domain.vo.SysSessionVo;

import java.util.List;

/**
 * 电影场次服务层
 *
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysSessionService {
    List<SysSession> findByVo(SysSessionVo sysSessionVo);

    List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession);

    SysSession findSessionById(Long id);

    SysSession findOneSession(Long id);

    int addSession(SysSession sysSession);

    int updateSession(SysSession sysSession);

    int deleteSession(Long[] id);

    List<SysSession> findSessionByMovieId(Long movieId);

}
