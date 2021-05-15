package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.response.ModifyPatchMemberResponse;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.dto.request.ModifyPutMemberRequest;
import com.ssafy.thxstore.member.dto.response.ModifyPutMemberResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    public ModifyPatchMemberResponse patchMember(String email, ModifyPatchMemberRequest modifyPatchMemberRequest) throws IOException {
        Member existingMember = memberRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        if (modifyPatchMemberRequest.getNickname() != null) {
            existingMember.setNickname(modifyPatchMemberRequest.getNickname());
        } else if (modifyPatchMemberRequest.getPhoneNumber() != null) {
            existingMember.setPhoneNumber(modifyPatchMemberRequest.getPhoneNumber());
        } else if (modifyPatchMemberRequest.getPassword() != null) {
            existingMember.setPassword(modifyPatchMemberRequest.getPassword());
        } else if (modifyPatchMemberRequest.getProfileImage() != null){
            String imgProfile = imageService.createImage(modifyPatchMemberRequest.getProfileImage());
            existingMember.setProfileImage(imgProfile);
        }else{
            throw new IOException(String.valueOf(ErrorCode.INVALID_INPUT_VALUE));
        }
        Member member = this.memberRepository.save(existingMember);
        return ModifyPatchMemberResponse.of(member);
    }

    public ModifyPutMemberResponse putMember(String email, ModifyPutMemberRequest modifyPutMemberRequest){
        Member existingMember = memberRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
        modelMapper.map(modifyPutMemberRequest,existingMember);
        Member member =this.memberRepository.save(existingMember);
        return ModifyPutMemberResponse.of(member);
    }

    public Member deleteMember(String email) {
        Member existingMember = memberRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        memberRepository.deleteById(existingMember.getId());
        return existingMember;
    }
}
