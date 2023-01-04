import React, { useEffect, useState } from 'react';
import { nicknameChange, userInfo } from '../user_action';

function UserInfoPage() {

  const [nickname, setNickname] = useState('');
  const [grade, setGrade] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    userInfo(setNickname,setGrade,setErrorMessage);
  },[]);

  const nicknameChangeButton = (e) => {
    let UserInfoDto = {
      nickname : nickname
    }
    nicknameChange(UserInfoDto,setNickname,setErrorMessage);
  }

    return (
        <div className="App">
        <h1>유저 정보 화면</h1>
        <div>
          {grade!==undefined && grade.length>0 &&
            <input
              type="nickname"
              name="nickname"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
          }
          {grade!==undefined && grade.length>0 &&
            <button onClick={() =>  nicknameChangeButton()}>
              닉네임 변경
            </button>
          }
          {grade!==undefined && grade.length>0 &&
            <div>{grade}</div>
          }
          {errorMessage!==undefined && errorMessage.length>0 && <div>{errorMessage}</div>}
        </div>
      </div>
    )
}

export default UserInfoPage;