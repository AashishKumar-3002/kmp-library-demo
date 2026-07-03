#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class UOCOfferEvent, UOCOfferDecision, UOCOfferSdkConfig, UOCOfferAttribution, UOCOfferUser, UOCKotlinEnumCompanion, UOCKotlinEnum<E>, UOCOfferEnvironment, UOCKotlinArray<T>, UOCOfferEngine;

@protocol UOCPlatformInfo, UOCKotlinComparable, UOCKotlinIterator;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface UOCBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface UOCBase (UOCBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface UOCMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface UOCMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorUOCKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface UOCNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface UOCByte : UOCNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface UOCUByte : UOCNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface UOCShort : UOCNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface UOCUShort : UOCNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface UOCInt : UOCNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface UOCUInt : UOCNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface UOCLong : UOCNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface UOCULong : UOCNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface UOCFloat : UOCNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface UOCDouble : UOCNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface UOCBoolean : UOCNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((swift_name("PlatformInfo")))
@protocol UOCPlatformInfo
@required
- (NSString *)name __attribute__((swift_name("name()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DefaultPlatformInfo")))
@interface UOCDefaultPlatformInfo : UOCBase <UOCPlatformInfo>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)name __attribute__((swift_name("name()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferAttribution")))
@interface UOCOfferAttribution : UOCBase
- (instancetype)initWithSource:(NSString *)source campaign:(NSString *)campaign __attribute__((swift_name("init(source:campaign:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *campaign __attribute__((swift_name("campaign")));
@property (readonly) NSString *source __attribute__((swift_name("source")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferDecision")))
@interface UOCOfferDecision : UOCBase
- (instancetype)initWithTitle:(NSString *)title rewardText:(NSString *)rewardText eligible:(BOOL)eligible widgetUrl:(NSString *)widgetUrl debugSummary:(NSString *)debugSummary platform:(NSString *)platform __attribute__((swift_name("init(title:rewardText:eligible:widgetUrl:debugSummary:platform:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *debugSummary __attribute__((swift_name("debugSummary")));
@property (readonly) BOOL eligible __attribute__((swift_name("eligible")));
@property (readonly) NSString *platform __attribute__((swift_name("platform")));
@property (readonly) NSString *rewardText __attribute__((swift_name("rewardText")));
@property (readonly) NSString *title __attribute__((swift_name("title")));
@property (readonly) NSString *widgetUrl __attribute__((swift_name("widgetUrl")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferEngine")))
@interface UOCOfferEngine : UOCBase
- (instancetype)initWithPlatformInfo:(id<UOCPlatformInfo>)platformInfo __attribute__((swift_name("init(platformInfo:)"))) __attribute__((objc_designated_initializer));
- (void)emitEventEvent:(UOCOfferEvent *)event __attribute__((swift_name("emitEvent(event:)")));
- (UOCOfferDecision *)evaluateCartValue:(int64_t)cartValue currency:(NSString *)currency __attribute__((swift_name("evaluate(cartValue:currency:)")));
- (void)initializeConfig:(UOCOfferSdkConfig *)config __attribute__((swift_name("initialize(config:)")));
- (void)setAttributionAttribution:(UOCOfferAttribution * _Nullable)attribution __attribute__((swift_name("setAttribution(attribution:)")));
- (void)setUserUser:(UOCOfferUser * _Nullable)user __attribute__((swift_name("setUser(user:)")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol UOCKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface UOCKotlinEnum<E> : UOCBase <UOCKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) UOCKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly, getter=name_) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferEnvironment")))
@interface UOCOfferEnvironment : UOCKotlinEnum<UOCOfferEnvironment *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) UOCOfferEnvironment *qa __attribute__((swift_name("qa")));
@property (class, readonly) UOCOfferEnvironment *prod __attribute__((swift_name("prod")));
+ (UOCKotlinArray<UOCOfferEnvironment *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<UOCOfferEnvironment *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferEvent")))
@interface UOCOfferEvent : UOCBase
- (instancetype)initWithName:(NSString *)name value:(NSString *)value __attribute__((swift_name("init(name:value:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferSdkConfig")))
@interface UOCOfferSdkConfig : UOCBase
- (instancetype)initWithMerchantId:(NSString *)merchantId environment:(UOCOfferEnvironment *)environment widgetBaseUrl:(NSString *)widgetBaseUrl __attribute__((swift_name("init(merchantId:environment:widgetBaseUrl:)"))) __attribute__((objc_designated_initializer));
@property (readonly) UOCOfferEnvironment *environment __attribute__((swift_name("environment")));
@property (readonly) NSString *merchantId __attribute__((swift_name("merchantId")));
@property (readonly) NSString *widgetBaseUrl __attribute__((swift_name("widgetBaseUrl")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OfferUser")))
@interface UOCOfferUser : UOCBase
- (instancetype)initWithId:(NSString *)id loyaltyTier:(NSString *)loyaltyTier __attribute__((swift_name("init(id:loyaltyTier:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString *id __attribute__((swift_name("id")));
@property (readonly) NSString *loyaltyTier __attribute__((swift_name("loyaltyTier")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UnloqOffersCoreClient")))
@interface UOCUnloqOffersCoreClient : UOCBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithEngine:(UOCOfferEngine *)engine __attribute__((swift_name("init(engine:)"))) __attribute__((objc_designated_initializer));
- (void)emitEventEvent:(UOCOfferEvent *)event __attribute__((swift_name("emitEvent(event:)")));
- (UOCOfferDecision *)evaluateCartValue:(int64_t)cartValue currency:(NSString *)currency __attribute__((swift_name("evaluate(cartValue:currency:)")));
- (void)initializeConfig:(UOCOfferSdkConfig *)config __attribute__((swift_name("initialize(config:)")));
- (void)setAttributionAttribution:(UOCOfferAttribution * _Nullable)attribution __attribute__((swift_name("setAttribution(attribution:)")));
- (void)setUserUser:(UOCOfferUser * _Nullable)user __attribute__((swift_name("setUser(user:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface UOCKotlinEnumCompanion : UOCBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) UOCKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface UOCKotlinArray<T> : UOCBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(UOCInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<UOCKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol UOCKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
