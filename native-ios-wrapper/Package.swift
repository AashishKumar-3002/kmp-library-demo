// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "NativeIosWrapperDemo",
    platforms: [
        .iOS(.v16),
        .macOS(.v13)
    ],
    products: [
        .library(
            name: "NativeIosWrapperDemo",
            type: .static,
            targets: ["NativeIosWrapperDemo"]
        )
    ],
    targets: [
        .binaryTarget(
            name: "UnloqOffersCore",
            path: "../shared-core/build/XCFrameworks/release/UnloqOffersCore.xcframework"
        ),
        .target(
            name: "NativeIosWrapperDemo",
            dependencies: ["UnloqOffersCore"]
        ),
        .testTarget(
            name: "NativeIosWrapperDemoTests",
            dependencies: ["NativeIosWrapperDemo"]
        )
    ]
)
