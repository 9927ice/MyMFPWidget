//
//  TodayViewController.m
//  myWidget
//
//  Created by Andrew on 2017/11/6.
//
//

#import "TodayViewController.h"
#import <NotificationCenter/NotificationCenter.h>
#import <IBMMobileFirstPlatformFoundation/IBMMobileFirstPlatformFoundation.h>

@interface TodayViewController () <NCWidgetProviding>
@property (strong, nonatomic) UIButton *btn;
@property (strong, nonatomic) UILabel *label;
@property (nonatomic) int count;
@end

@implementation TodayViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    static dispatch_once_t once;
    
    self.preferredContentSize = CGSizeMake(0, 50);
    
    _btn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, [[UIScreen mainScreen] bounds].size.width, 80)];
    _btn.backgroundColor = [UIColor redColor];
    [_btn addTarget:self action:@selector(btnClick) forControlEvents:UIControlEventTouchUpInside];
    _label = [[UILabel alloc] initWithFrame:CGRectMake(0, 100, [[UIScreen mainScreen] bounds].size.width, 80)];
    
    dispatch_once(&once, ^ {
        [_btn setTitle:@"Start" forState:UIControlStateNormal];
        [_label setText:@"- - -"];
    });
    
    
    [self.view addSubview:_btn];
    
    if ([self.extensionContext respondsToSelector:@selector(setWidgetLargestAvailableDisplayMode:)]) { // iOS 10+
        [self.extensionContext setWidgetLargestAvailableDisplayMode:NCWidgetDisplayModeExpanded];
    } else {
        self.preferredContentSize = CGSizeMake(0, 200.0); // iOS 10-
    }
    
}

-(void) btnClick {
    
    /*
    NSURL *url = [NSURL URLWithString:@"ForExtension://someParam?param=123"];
    [self.extensionContext openURL:url completionHandler:nil];
    */
     
    
    NSUserDefaults *sharedDefaults = [[NSUserDefaults alloc] initWithSuiteName:@"group.fubon.demoApp"];
    NSString *data = [sharedDefaults stringForKey:@"myData"];
    
    NSString *valueToSave = @"Man";
    NSUserDefaults *sharedDefaults123 = [[NSUserDefaults alloc] initWithSuiteName:@"group.fubon.demoApp"];
    [sharedDefaults setObject:valueToSave forKey:@"toApp"];
    [sharedDefaults synchronize];
    
    
    NSURL *url = [NSURL URLWithString:@"http://10.1.3.164:9080/mfp/api/adapters/httpAdapter/unprotected"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:[NSOperationQueue mainQueue]
                           completionHandler:^(NSURLResponse *response,
                                               NSData *data, NSError *connectionError)
     {
         if (data.length > 0 && connectionError == nil)
         {
             NSDictionary *greeting = [NSJSONSerialization JSONObjectWithData:data
                                                                      options:0
                                                                        error:NULL][@"result"];
             
             NSString * result = [greeting objectForKey:@"key"];
             
             NSArray *listItems = [result componentsSeparatedByString:@","];
             NSArray *timeArray = [listItems[1] componentsSeparatedByString:@"updatetime:"];
             
              [_btn setTitle:listItems[0] forState:UIControlStateNormal];
              [_label setText: timeArray[1]];
         }
     }];
     
   
}

- (void)widgetActiveDisplayModeDidChange:(NCWidgetDisplayMode)activeDisplayMode
                         withMaximumSize:(CGSize)maxSize {
    
    if (activeDisplayMode == NCWidgetDisplayModeExpanded) {
        self.preferredContentSize = CGSizeMake(maxSize.width, 200.0);
        [self.view addSubview:_label];
    } else if (activeDisplayMode == NCWidgetDisplayModeCompact) {
        self.preferredContentSize = maxSize;
        [_label removeFromSuperview];
    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)widgetPerformUpdateWithCompletionHandler:(void (^)(NCUpdateResult))completionHandler {
    // Perform any setup necessary in order to update the view.
    
    // If an error is encountered, use NCUpdateResultFailed
    // If there's no update required, use NCUpdateResultNoData
    // If there's an update, use NCUpdateResultNewData

    completionHandler(NCUpdateResultNewData);
}

@end
