package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.zsg.entity.po.TalkPO;
import com.hnust.zsg.mapper.TalkMapper;
import com.hnust.zsg.service.TalkService;
import org.springframework.stereotype.Service;

@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, TalkPO> implements TalkService {
}
