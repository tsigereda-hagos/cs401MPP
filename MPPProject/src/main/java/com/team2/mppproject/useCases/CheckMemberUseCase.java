package com.team2.mppproject.useCases;

import com.team2.mppproject.business.LibraryMember;




public interface CheckMemberUseCase {
	public boolean checkMember(String memberId);

	public LibraryMember getMember(String memberId);
}
