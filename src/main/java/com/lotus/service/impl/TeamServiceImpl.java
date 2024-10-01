package com.lotus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lotus.pojo.Team;
import com.lotus.service.TeamService;
import com.lotus.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author 31964
* @description 针对表【team】的数据库操作Service实现
* @createDate 2024-10-01 12:21:25
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




