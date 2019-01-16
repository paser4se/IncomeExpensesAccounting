import { html } from '@polymer/lit-element';

export const DropZoneStyles = html`
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  
  <script src="https://unpkg.com/@webcomponents/webcomponentsjs@2.0.0/webcomponents-loader.js"></script>
  <script src="https://unpkg.com/wired-elements@latest/dist/wired-elements.bundled.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.16/js/mdb.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.16/css/mdb.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>

<style>
    .drop-zone
    {
        background-color: rgb(200, 218, 223);
        opacity: 0.9;
        outline: 2px dashed rgb(15, 60, 75);
        outline-offset: -6px;
        margin-left: auto;
        margin-right: auto;
        margin-top: 20px;
        padding: 10px;
        width: 50%;
        height: 230px;
        text-align: center;
        position: relative;
    }
    .drop-zone.drop-content
    {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .drop-zone.entered
    {
        background-color: rgb(231, 241, 243);
        outline: 3px dashed rgb(15, 60, 75);
    }
    label
    {
        color: #0f3c4b;
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        font-size: 24px;
    }
    label:hover, button:hover
    {
        color: #39bfd3;
    }
    span
    {
        color: #0f3c4b !important;
    }
    button
    {
        font-weight: 700;
        color: white !important;
        background-color: #39bfd3;
        display: block;
        padding: 8px 16px;
        margin: 40px auto 0;
        border-radius: 5px;
        border: none;
    }
    button:hover
    {
        background-color: #0f3c4b;
    }
</style>
`;
