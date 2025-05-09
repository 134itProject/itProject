package com.marre.cm.service.impl;


import com.marre.cm.domain.SysSession;
import com.marre.cm.domain.vo.SysSessionVo;
import com.marre.cm.mapper.SysSessionMapper;
import com.marre.cm.service.SysSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Service
public class SysSessionServiceImpl implements SysSessionService {

    @Resource
    private SysSessionMapper sysSessionMapper;

    @Override
    public List<SysSession> findByVo(SysSessionVo sysSessionVo) {
        return sysSessionMapper.findByVo(sysSessionVo);
    }

    @Override
    public List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession) {
        return sysSessionMapper.findSessionByMovieIdOrHallId(sysSession);
    }

    @Override
    public SysSession findSessionById(Long id) {
        return sysSessionMapper.findSessionById(id);
    }

    @Override
    public SysSession findOneSession(Long id) {
        return sysSessionMapper.findOneSession(id);
    }

    @Override
    public int addSession(SysSession sysSession) {
        return sysSessionMapper.addSession(sysSession);
    }

    @Override
    public int updateSession(SysSession sysSession) {
        return sysSessionMapper.updateSession(sysSession);
    }

    @Override
    public int deleteSession(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysSessionMapper.deleteSession(id);
        }
        return rows;
    }

    @Override
    public List<SysSession> findSessionByMovieId(Long movieId) {
        return sysSessionMapper.findSessionByMovieId(movieId);
    }
}
