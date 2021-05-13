package com.ssafy.thxstore.member.service;

import com.ssafy.thxstore.image.service.ImageService;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
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

    public Member patchMember(String email, ModifyPatchMemberRequest modifyPatchMemberRequest) throws IOException {
        Member existingMember = memberRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        if (modifyPatchMemberRequest.getNickname() != null) {
            this.modelMapper.map(modifyPatchMemberRequest.getNickname(), existingMember.getNickname());
        } else if (modifyPatchMemberRequest.getPhoneNumber() != null) {
            this.modelMapper.map(modifyPatchMemberRequest.getPhoneNumber(), existingMember.getPhoneNumber());
        } else if (modifyPatchMemberRequest.getPassword() != null) {
            this.modelMapper.map(modifyPatchMemberRequest.getPassword(), existingMember.getPassword());
        } else if (modifyPatchMemberRequest.getProfileImage() != null) {
            String imgProfile = imageService.createImage(modifyPatchMemberRequest.getProfileImage());
            this.modelMapper.map(imgProfile, existingMember.getProfileImage());
        }
        return this.memberRepository.save(existingMember);
    }

    public Member deleteMember(String email) {
        Member existingMember = memberRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        memberRepository.deleteById(existingMember.getId());
        return existingMember;
    }
}
