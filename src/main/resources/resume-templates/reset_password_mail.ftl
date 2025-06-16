<!DOCTYPE html>
<html lang="en" data-theme="light">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  
  <style>
    .pb-2 {
      padding-bottom: 1rem;
    }

    .pb-1 {
      padding-bottom: 0.5rem;
    }

    .regards {
      padding-top: 2rem;
    }

    .otp-box {
      font-weight: bold;
      background-color: #f3f3f3;
      padding: 5px 10px;
      display: inline-block;
      border-radius: 6px;
      letter-spacing: 3px;
    }
  </style>

</head>

<body>
  <div>
    <div class="pb-2">Dear ${recipientName},</div>
    <div class="pb-2">Your One-Time Password (OTP) is:</div>
    <div class="pb-2">
      <div class="otp-box">${otp}</div>
    </div>
    <div class="pb-2">This OTP is valid for 10 minutes. Please do not share it with anyone.</div>
    <div class="regards">Regards,</div>
    <div><em>Make Profiles</em></div>
  </div>
</body>

</html>