require 'xcodeproj'
project_path = 'iosApp/iosApp.xcodeproj'
project = Xcodeproj::Project.open(project_path)

project.targets.each do |target|
  target.build_configurations.each do |config|
    flags = config.build_settings['OTHER_LDFLAGS'] || ['$(inherited)']
    # Add linker flags to search derived data
    flags << '-F/tmp/unloq-kmp-ios-derived-data/Build/Products/Debug-iphonesimulator/PackageFrameworks'
    flags << '-F/tmp/unloq-kmp-ios-derived-data/Build/Products/Debug-iphoneos/PackageFrameworks'
    flags << '-framework'
    flags << 'UnloqOffersCore'
    flags << '/tmp/unloq-kmp-ios-derived-data/Build/Products/Debug-iphonesimulator/NativeIosWrapperDemo.o'
    config.build_settings['OTHER_LDFLAGS'] = flags
  end
end
project.save
