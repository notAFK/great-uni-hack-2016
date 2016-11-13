<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class UploadController extends Controller
{
  public function upload_single($id)
  {
    $base64_string = $_POST['image'];

    if(!file_exists(base_path() . "/public/uploads/" . $id)) {
      mkdir(base_path() . "/public/uploads/" . $id);
    }
    $ifp = fopen(base_path() . "/public/uploads/" . $id . "/" . str_random(20) . '.jpg', 'wb'); 

    //$data = explode(',', $base64_string);

    fwrite($ifp, base64_decode($base64_string)); 
    fclose($ifp); 
  }
}