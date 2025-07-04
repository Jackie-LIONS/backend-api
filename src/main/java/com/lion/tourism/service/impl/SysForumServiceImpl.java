package com.lion.tourism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.tourism.domain.SysForum;
import com.lion.tourism.mapper.SysForumMapper;
import com.lion.tourism.service.SysForumService;
import org.springframework.stereotype.Service;

@Service
public class SysForumServiceImpl extends ServiceImpl<SysForumMapper, SysForum> implements SysForumService {
}
