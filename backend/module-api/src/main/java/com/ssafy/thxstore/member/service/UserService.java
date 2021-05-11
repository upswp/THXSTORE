package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;



}
