//*****************************************************************************
// This file is part of bluetrack.
//
// bluetrack is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// bluetrack is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with bluetrack.  If not, see <http://www.gnu.org/licenses/>.
//*****************************************************************************

package org.jonblack.bluetrack.storage;

import org.jonblack.bluetrack.Session;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SessionTable
{
  private static final String TAG = "SessionTable";
  
  // Database table metadata
  // TODO: Some of this might be better off in a DeviceContract class.
  public static final String TABLE_NAME = "session";
  public static final String COL_ID = Session._ID;
  public static final Uri CONTENT_URI = Uri.parse("content://" + BluetrackContentProvider.AUTHORITY + "/session");
  
  // Database creation SQL statement
  private static final String DATABASE_CREATE = 
      "CREATE TABLE " + TABLE_NAME + 
      " (" +
      COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
      "start_date_time TEXT NOT NULL," +
      "end_date_time TEXT);";
  
  public static void onCreate(SQLiteDatabase db)
  {
    Log.i(TAG, String.format("Creating table '%s'", TABLE_NAME));
    
    db.execSQL(DATABASE_CREATE);
  }
  
  public static void onUpgrade(SQLiteDatabase db, int oldVersion,
                               int newVersion)
  {
    Log.i(TAG, String.format("Upgrading table '%s' version from %d to %d",
                             TABLE_NAME, oldVersion, newVersion));
    
    switch (oldVersion)
    {
    case 1:
      // No upgrade from 1 to 2
    case 2:
      // No upgrade from 2 to 3
      break;
    default:
      assert(false);
    }
  }
}
