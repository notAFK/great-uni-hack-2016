<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return redirect('/events');
});

Auth::routes();

Route::group(['prefix' => '/events'], function() {
  Route::get('/add', 'EventController@create')->middleware('auth');
  Route::post('/add', 'EventController@store')->middleware('auth');
  Route::get('/', 'EventController@index');
  Route::get('/{id}', 'EventController@show_get');
  Route::post('/{id}', 'EventController@show_post');
  Route::get('/edit/{id}', 'EventController@edit')->middleware('auth');
  Route::post('/edit/{id}', 'EventController@update')->middleware('auth');
});

Route::group(['prefix' => 'api'], function() {
  Route::post('/upload/{id}', 'Api\UploadController@upload_single');

  Route::get('/check/{lat}/{lng}', function($lat, $lng) {

    $query = "SELECT *, ( 3959 * acos( cos( radians(" . $lat . ") ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(" . $lng . ") ) + sin( radians(" . $lat . ") ) * sin( radians( lat ) ) ) ) AS distance
                  FROM events
                  WHERE NOW()
                  BETWEEN start_date AND end_date
                  HAVING distance < 0.1
                  ORDER BY distance";
    $events = DB::select($query);

    $result = [];
    foreach ($events as $key => $event) {
      array_push($result, ["id" => $event->id, "title" => $event->title]);
    }
    header('Content-Type: application/json');
    echo json_encode($result);
    exit();
  });
  Route::get('/pass/{id}/{pass}', function($id, $pass) {
    $event = App\Event::find($id);
    if(!$event) {
      echo json_encode(["status" => "error", "message" => "Invalid id."]);
      exit();
    }
    if($event->password != $pass) {
      echo json_encode(["status" => "error", "message" => "Wrong pass."]);
      exit();
    }

    echo json_encode(["status" => "success", "id" => $event->id]);
    exit();
  });
});