import React, { useState } from 'react';

function RegisterPage() {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
  
    const handleSubmit = (e) => {
      e.preventDefault();
      console.log(`submit! ${userId} ${password} ${nickname}`);
    };

    //생일 영역//////////////////////////////////////////////////////////////////////////////
    const [form, setForm] = useState({
        year: new Date().getFullYear(),
        month: "01",
        day: "01",
      });

    const now = new Date();
  
    let years = [];
    for (let y = now.getFullYear(); y >= 1930; y -= 1) {
        years.push(y);
    }

    let month = [];
    for (let m = 1; m <= 12; m += 1) {
        if (m < 10) {
        // 날짜가 2자리로 나타나야 했기 때문에 1자리 월에 0을 붙혀준다
        month.push("0" + m.toString());
        } else {
        month.push(m.toString());
        }
    }
    let days = [];
    let date = new Date(form.year, form.month, 0).getDate();
    for (let d = 1; d <= date; d += 1) {
        if (d < 10) {
        // 날짜가 2자리로 나타나야 했기 때문에 1자리 일에 0을 붙혀준다
        days.push("0" + d.toString());
        } else {
        days.push(d.toString());
        }
    }

    //화면 영역/////////////////////////////////////////////////////////////////////////////////////
    return (
      <div className="App">
        <h1>회원가입</h1>
        <form onSubmit={handleSubmit}>
          <input
            name="userId"
            value={userId}
            placeholder="아이디"
            onChange={(e) => setUserId(e.target.value)}
          />
          <div />
          <input
            type="password"
            name="password"
            value={password}
            placeholder="비밀번호"
            onChange={(e) => setPassword(e.target.value)}
          />
          <div />
          <input
            type="nickname"
            name="nickname"
            value={nickname}
            placeholder="닉네임"
            onChange={(e) => setNickname(e.target.value)}
          />
          <div />
          <select
                value={form.years}
                onChange={(e) =>
                    setForm({ ...form, years: e.target.value })
                }
            >
                {years.map(item => (
                    <option value={item} key={item}>
                        {item}
                    </option>
                ))}
            </select>
          <select
                value={form.month}
                onChange={(e) =>
                    setForm({ ...form, month: e.target.value })
                }
            >
                {month.map(item => (
                    <option value={item} key={item}>
                        {item}
                    </option>
                ))}
            </select>
            <select
                value={form.day}
                onChange={(e) =>
                    setForm({ ...form, day: e.target.value })
                }
            >
                {days.map(item => (
                    <option value={item} key={item}>
                        {item}
                    </option>
                ))}
            </select>
          <div />
          <input type="submit" />
        </form>
      </div>
    );
  }
export default RegisterPage;