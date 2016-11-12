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
    return view('welcome');
});

Auth::routes();

Route::get('/home', 'HomeController@index');

Route::group(['/events'], function() {
  Route::get('/', 'EventController@index');
  Route::get('/{id}', 'EventController@show');
  Route::get('/add', 'EventController@create')->middleware('auth');
  Route::post('/add', 'EventController@store')->middleware('auth');
  Route::get('/edit/{id}', 'EventController@edit')->middleware('auth');
  Route::post('/edit/{id}', 'EventController@update')->middleware('auth');
});