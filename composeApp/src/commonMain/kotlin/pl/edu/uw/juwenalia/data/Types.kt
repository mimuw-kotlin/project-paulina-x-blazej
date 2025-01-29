package pl.edu.uw.juwenalia.data

import io.github.vinceglb.filekit.core.PlatformFile
import io.realm.kotlin.types.RealmObject

class FileData : RealmObject {
    var filePath : String? = null
    var platformFile : PlatformFile? = null
}