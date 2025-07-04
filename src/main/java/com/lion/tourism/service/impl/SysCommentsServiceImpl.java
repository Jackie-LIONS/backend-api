package com.lion.tourism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.tourism.domain.SysComments;
import com.lion.tourism.mapper.SysCommentsMapper;
import com.lion.tourism.service.SysCommentsService;
import org.springframework.stereotype.Service;

@Service
public class SysCommentsServiceImpl extends ServiceImpl<SysCommentsMapper, SysComments> implements SysCommentsService {
}
