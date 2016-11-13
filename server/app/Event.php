<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Event extends Model
{
  protected $table = 'events';

  public function organiser() {
    return $this->belongsTo('App\User');
  }

  public function getStartDateAttribute($value) {
    return date('h:i, d.m.Y', strtotime($value));
  }

  public function getEndDateAttribute($value) {
    return date('h:i, d.m.Y', strtotime($value));
  }

  public function getTitleAttribute($value) {
    return ucwords($value);
  }
}
