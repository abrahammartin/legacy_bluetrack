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

import org.jonblack.bluetrack.Device;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DeviceTable
{
  private static final String TAG = "DeviceTable";
  
  // Database table metadata
  // TODO: Some of this might be better off in a DeviceContract class.
  public static final String TABLE_NAME = "device";
  public static final String COL_ID = Device._ID;
  public static final Uri CONTENT_URI = Uri.parse("content://" + BluetrackContentProvider.AUTHORITY + "/device");
  
  // Database creation SQL statement
  private static final String DATABASE_CREATE = 
      "CREATE TABLE " + TABLE_NAME + 
      " (" +
      COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
      "mac_address TEXT NOT NULL UNIQUE," +
      "name TEXT NOT NULL," +
      "minor_class INTEGER," +
      "major_class INTEGER);";
  
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
      upgradeF2T3(db);
      break;
    default:
      assert(false);
    }
  }
  
  /**
   * Upgrades the table from database version 1 to version 2.
   * 
   * Adds two new fields:
   *   - minor_class
   *   - major_class
   * 
   * @param db
   */
  private static void upgradeF2T3(SQLiteDatabase db)
  {
    String sql = "ALTER TABLE " + TABLE_NAME +
                 " ADD COLUMN minor_class INTEGER;\n";
    sql += "ALTER TABLE " + TABLE_NAME +
           " ADD COLUMN major_class INTEGER;\n";
    db.execSQL(sql);
  }
}
