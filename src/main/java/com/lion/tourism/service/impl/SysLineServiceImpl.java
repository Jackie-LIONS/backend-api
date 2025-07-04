package com.lion.tourism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.tourism.domain.SysLine;
import com.lion.tourism.mapper.SysLineMapper;
import com.lion.tourism.service.SysLineService;
import org.springframework.stereotype.Service;

@Service
public class SysLineServiceImpl extends ServiceImpl<SysLineMapper, SysLine> implements SysLineService {
}
