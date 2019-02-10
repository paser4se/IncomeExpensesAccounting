import { html } from '@polymer/lit-element';

export const FooterStyle =  html`
<style>
    .footer-distributed{
        background-color: #292c2f;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
        box-sizing: border-box;
        width: 100%;
        font: bold 16px sans-serif;
        text-align: left;
    
        padding: 50px 60px 40px;
        overflow: hidden;
    }
    
    /* Footer left */
    
    .footer-distributed .footer-left{
        float: left;
    }
    
    /* The company logo */
    
    .footer-distributed span{
        color:  #288b9e;
        font-size: 45px;
    }
    
    .footer-distributed img{
        height: 50px;
    }
    
    /* Footer Right */
    
    .footer-distributed .footer-right{
        float: right;
    }
    
    .footer-distributed .footer-right p{
        display: inline-block;
        vertical-align: top;
        margin: 15px 42px 0 0;
        color: #ffffff;
        font-size: 25px;
    }
    
    /* The contact form */
    
    .footer-distributed form{
        display: inline-block;
    }
    
    .footer-distributed form input,
    .footer-distributed form textarea{
        display: block;
        border-radius: 3px;
        box-sizing: border-box;
        background-color:  #1f2022;
        box-shadow: 0 1px 0 0 rgba(255, 255, 255, 0.1);
        border: none;
        resize: none;
    
        font: inherit;
        font-size: 14px;
        font-weight: normal;
        color:  #d1d2d2;
    
        width: 600px;
        padding: 18px;
    }
    
    .footer-distributed ::-webkit-input-placeholder {
        color:  #5c666b;
    }
    
    .footer-distributed ::-moz-placeholder {
        color:  #5c666b;
        opacity: 1;
    }
    
    .footer-distributed :-ms-input-placeholder{
        color:  #5c666b;
    }
    
    
    .footer-distributed form input{
        height: 55px;
        margin-bottom: 15px;
    }
    
    .footer-distributed form textarea{
        height: 100px;
        margin-bottom: 20px;
    }
    
    .footer-distributed form button{
        border-radius: 3px;
        background-color:  #33383b;
        color: #ffffff;
        border: 0;
        padding: 15px 50px;
        font-weight: bold;
        float: right;
    }
    
    /* If you don't want the footer to be responsive, remove these media queries */
    
    @media (max-width: 1000px) {
    
        .footer-distributed {
            font: bold 14px sans-serif;
        }
    
        .footer-distributed .footer-company-name{
            font-size: 12px;
        }
    
        .footer-distributed form input,
        .footer-distributed form textarea{
            width: 250px;
        }
    
        .footer-distributed form button{
            padding: 10px 35px;
        }
    
    }
    
    @media (max-width: 800px) {
    
        .footer-distributed{
            padding: 30px;
        }
    
        .footer-distributed .footer-left,
        .footer-distributed .footer-right{
            float: none;
            max-width: 300px;
            margin: 0 auto;
        }
    
        .footer-distributed .footer-left{
            margin-bottom: 40px;
        }
    
        .footer-distributed form{
            margin-top: 30px;
        }
    
        .footer-distributed form{
            display: block;
        }
    
        .footer-distributed form button{
            float: none;
        }
    }
</style>
`;
