package com.lion.tourism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.tourism.domain.SysHotelOrder;
import com.lion.tourism.mapper.SysHotelOrderMapper;
import com.lion.tourism.service.SysHotelOrderService;
import org.springframework.stereotype.Service;

@Service
public class SysHotelOrderServiceImpl extends ServiceImpl<SysHotelOrderMapper, SysHotelOrder> implements SysHotelOrderService {
}
